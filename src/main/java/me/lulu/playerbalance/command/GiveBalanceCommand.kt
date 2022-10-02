package me.lulu.playerbalance.command

import me.lulu.playerbalance.Cfg
import me.lulu.playerbalance.Config
import me.lulu.playerbalance.service.BalanceService
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class GiveBalanceCommand(val balanceService: BalanceService) : CommandBase() {

    override fun command(sender: CommandSender, label: String, args: Array<out String>) {
        val player = getPlayer()
        val target = args.getOrNull(0)?.let { Bukkit.getPlayer(it) } ?: fail(Cfg.GIVE_BALANCE_USAGE)
        val amount = args.getOrNull(1)?.toIntOrNull() ?: fail(Cfg.GIVE_BALANCE_USAGE)

        balanceService.giveBalance(player, target, amount)
    }

}