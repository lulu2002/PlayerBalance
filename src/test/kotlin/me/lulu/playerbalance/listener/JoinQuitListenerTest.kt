package me.lulu.playerbalance.listener

import be.seeseemelk.mockbukkit.entity.PlayerMock
import io.mockk.mockk
import io.mockk.verify
import me.lulu.playerbalance.BukkitTestBase
import me.lulu.playerbalance.service.BalanceService
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.junit.jupiter.api.Test

internal class JoinQuitListenerTest : BukkitTestBase() {

    @Test
    fun playerJoin_shouldCallServiceToLoadData() {
        val balanceService = mockk<BalanceService>(relaxed = true)
        val joinQuitListener = JoinQuitListener(balanceService)
        val player = PlayerMock(server, "testPlayer")

        joinQuitListener.onPlayerJoin(AsyncPlayerPreLoginEvent(player.name, mockk(), player.uniqueId))

        verify { balanceService.loadBalanceData(player.uniqueId) }
    }

    @Test
    fun playerQuit_shouldCallServiceToSaveData() {
        val balanceService = mockk<BalanceService>(relaxed = true)
        val joinQuitListener = JoinQuitListener(balanceService)
        val player = PlayerMock(server, "testPlayer")

        joinQuitListener.onPlayerQuit(PlayerQuitEvent(player,""))

        verify { balanceService.saveBalanceData(player.uniqueId) }
    }

}