package me.lulu.playerbalance.listener

import me.lulu.playerbalance.service.BalanceService
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

class JoinQuitListener(private val balanceService: BalanceService) : Listener {

    @EventHandler
    fun onPlayerJoin(e: AsyncPlayerPreLoginEvent) {
        this.balanceService.loadBalanceData(e.uniqueId)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        this.balanceService.saveBalanceData(e.player.uniqueId)
    }

}