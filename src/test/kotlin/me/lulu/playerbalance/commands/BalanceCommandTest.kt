package me.lulu.playerbalance.commands

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.spyk
import me.lulu.playerbalance.BukkitTestBase
import me.lulu.playerbalance.Config
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BalanceCommandTest : BukkitTestBase() {

    @Test
    fun withoutArgs_shouldReturnSelfBalance() {
        val player = server.addPlayer()

        mockkObject(plugin.balanceService)
        every { plugin.balanceService.getBalance(player) } returns 0.0

        player.performCommand("balance")

        assertEquals(player.nextMessage(), Config.BALANCE_SELF.replace("{balance}", "0.0"))
    }

}