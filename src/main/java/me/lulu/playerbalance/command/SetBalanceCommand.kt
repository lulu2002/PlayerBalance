package me.lulu.playerbalance.command

import me.lulu.playerbalance.Cfg
import me.lulu.playerbalance.Config
import me.lulu.playerbalance.service.BalanceService
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class SetBalanceCommand(private val service: BalanceService) : CommandBase() {

    override fun command(sender: CommandSender, label: String, args: Array<out String>) {
        val targetName = args.getOrNull(0) ?: fail(Cfg.SET_BALANCE_USAGE)
        val amount = args.getOrNull(1)?.toIntOrNull() ?: fail(Cfg.SET_BALANCE_USAGE)

        val target = Bukkit.getPlayer(targetName) ?: fail(Cfg.PLAYER_NOT_ONLINE.replace("{player}", targetName))

        service.setBalance(sender, target, amount)
    }

}