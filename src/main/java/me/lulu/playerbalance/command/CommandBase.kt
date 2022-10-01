package me.lulu.playerbalance.command

import me.lulu.playerbalance.Config
import me.lulu.playerbalance.extension.msg
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.RuntimeException

abstract class CommandBase : CommandExecutor {

    private lateinit var sender: CommandSender

    final override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        return try {
            this.sender = sender
            this.command(sender, label, args)
            true
        } catch (ex: CommandFailException) {
            ex.message?.let { sender.msg(it) }
            false
        }
    }

    protected fun <E : Any> fail(message: String): E {
        throw CommandFailException(message)
    }

    protected fun filter(boolean: Boolean, message: String) {
        if (!boolean)
            return fail(message)
    }

    protected fun getPlayer(): Player {
        filter(sender is Player, Config.PLAYER_ONLY)

        return sender as Player
    }

    abstract fun command(sender: CommandSender, label: String, args: Array<out String>)
}

class CommandFailException(message: String) : RuntimeException(message)