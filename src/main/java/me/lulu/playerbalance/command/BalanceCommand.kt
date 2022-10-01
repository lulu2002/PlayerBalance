package me.lulu.playerbalance.command

import me.lulu.playerbalance.Config
import me.lulu.playerbalance.service.BalanceService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BalanceCommand(val service: BalanceService) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player)
            return false

        sender.sendMessage(Config.BALANCE_SELF.replace("{balance}", service.getBalance(sender).toString()))

        return true
    }

}