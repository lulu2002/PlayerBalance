package me.lulu.playerbalance.command

import me.lulu.playerbalance.service.BalanceService
import org.bukkit.command.CommandSender

class EarnCommand(private val balanceService: BalanceService) : CommandBase() {

    override fun command(sender: CommandSender, label: String, args: Array<out String>) {
        val player = getPlayer()
        this.balanceService.earnRandomBalance(player)
    }

}