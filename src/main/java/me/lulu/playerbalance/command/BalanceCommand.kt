package me.lulu.playerbalance.command

import me.lulu.playerbalance.Config
import me.lulu.playerbalance.extension.msg
import me.lulu.playerbalance.service.BalanceService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BalanceCommand(val service: BalanceService) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player)
            return false

        val target = args.getOrNull(0)

        if (target == null) {
            this.selfBalance(sender)
        } else
            this.otherBalance(sender, target)
        return true
    }

    private fun otherBalance(player: Player, target: String) {

        Bukkit.getPlayer(target)?.let {
            player.msg(
                Config.BALANCE_OTHER
                    .replace("{player}", it.name)
                    .replace("{balance}", service.getBalance(it).toString())
            )
        }
    }

    private fun selfBalance(player: Player) {
        player.msg(Config.BALANCE_SELF.replace("{balance}", service.getBalance(player).toString()))
    }

}