package me.lulu.playerbalance.service

import be.seeseemelk.mockbukkit.entity.PlayerMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.lulu.playerbalance.BukkitTestBase
import me.lulu.playerbalance.Config
import me.lulu.playerbalance.extension.color
import me.lulu.playerbalance.module.CooldownModule
import me.lulu.playerbalance.module.RandomModule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals

internal class BalanceServiceTest : BukkitTestBase() {

    private val cooldownModule: CooldownModule = mockk(relaxed = true)
    private val randomModule: RandomModule = mockk(relaxed = true)
    private val service = BalanceService(cooldownModule, randomModule)

    @Nested
    inner class GetBalance {

        @Test
        fun playerNotExist_shouldReturnsZero() {
            val balance = service.getBalance(UUID.randomUUID())
            assertEquals(balance, 0)
        }

        @Test
        fun playerExist_shouldReturnsBalance() {
            val uuid = UUID.randomUUID()
            service.setBalanceRaw(uuid, 100)

            val balance = service.getBalance(uuid)
            assertEquals(balance, 100)
        }

    }

    @Nested
    inner class GiveBalance {

        lateinit var player: PlayerMock
        lateinit var target: PlayerMock

        @BeforeEach
        fun setup() {
            player = server.addPlayer()
            target = server.addPlayer()

            service.setBalanceRaw(player.uniqueId, 100)
        }

        @Test
        fun argIsNagiative_shouldFail() {
            service.giveBalance(player, target, -1)

            assertBalanceNotChange()
            assertEquals(player.nextMessage(), Config.ARG_IS_NEGATIVE.color())
        }

        @Test
        fun noEnoughBalance_shouldFail() {
            service.giveBalance(player, target, 101)

            assertBalanceNotChange()
            assertEquals(player.nextMessage(), Config.NO_ENOUGH_BALANCE.color())
        }

        @Test
        fun giveSelf_shouldFail() {
            service.giveBalance(player, player, 1)

            assertBalanceNotChange()
            assertEquals(player.nextMessage(), Config.CANT_GIVE_SELF_BALANCE.color())
        }

        @Test
        fun success_shouldRemoveFromAccountAndAddToTarget() {
            service.giveBalance(player, target, 10)

            assertEquals(service.getBalance(player), 90)
            assertEquals(service.getBalance(target), 10)

            assertEquals(
                player.nextMessage(), Config.GIVE_SUCCESS.color()
                    .replace("{amount}", "10")
                    .replace("{target}", target.name)
                    .replace("{balance}", "90")
            )
        }

        private fun assertBalanceNotChange() {
            assertEquals(service.getBalance(player.uniqueId), 100)
            assertEquals(service.getBalance(target.uniqueId), 0)
        }
    }

    @Nested
    inner class SetBalance {

        lateinit var player: PlayerMock
        lateinit var target: PlayerMock

        @BeforeEach
        fun setup() {
            player = server.addPlayer()
            target = server.addPlayer()
        }

        @Test
        fun noPermission_shouldFail() {
            service.setBalance(player, target, 100)

            assertEquals(service.getBalance(target), 0)
            assertEquals(player.nextMessage(), Config.NO_PERMISSION.color())
        }

        @Test
        fun valueIsNagative_shouldFail() {
            player.addAttachment(plugin, Config.SET_BALANCE_PERMISSION, true)

            service.setBalance(player, target, -1)

            assertEquals(service.getBalance(target), 0)
            assertEquals(player.nextMessage(), Config.ARG_IS_NEGATIVE.color())
        }

        @Test
        fun success_shouldUpdateTargetBalance() {
            player.addAttachment(plugin, Config.SET_BALANCE_PERMISSION, true)

            service.setBalance(player, target, 100)

            assertEquals(service.getBalance(target), 100)
            assertEquals(player.nextMessage(), Config.SET_BALANCE_SUCCESS.color())
        }

    }


    @Nested
    inner class EarnBalance {

        @Test
        fun playerIsInCooldown_shouldFail() {
            val player = server.addPlayer()

            every { cooldownModule.isInCooldown(player) } returns true
            every { cooldownModule.getCooldown(player) } returns 10000

            service.earnRandomBalance(player)

            assertEquals(player.nextMessage(), Config.EARN_COOLDOWN.color().replace("{seconds}", "10"))
            assertEquals(service.getBalance(player), 0)
        }

        @Test
        fun playerIsNotInCooldown_shouldEarn() {
            val player = server.addPlayer()

            every { cooldownModule.isInCooldown(player) } returns false
            every { randomModule.randomValue(any(), any()) } returns 3

            service.earnRandomBalance(player)

            assertEquals(
                player.nextMessage(),
                Config.EARN_SUCCESS.color()
                    .replace("{amount}", "3")
                    .replace("{balance}", "3")
            )

            assertEquals(service.getBalance(player), 3)
            verify { cooldownModule.setCooldown(player, Config.EARN_CD) }
        }
    }
}