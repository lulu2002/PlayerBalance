package me.lulu.playerbalance.service

import me.lulu.playerbalance.Config
import me.lulu.playerbalance.extension.color
import me.lulu.playerbalance.extension.msg
import me.lulu.playerbalance.module.CooldownModule
import me.lulu.playerbalance.module.RandomModule
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class BalanceService(
    private val cooldownModule: CooldownModule,
    private val randomModule: RandomModule
) {

    private val balances = mutableMapOf<UUID, Int>()

    fun getBalance(player: Player): Int {
        return getBalance(player.uniqueId)
    }

    fun getBalance(uuid: UUID): Int {
        return balances.getOrDefault(uuid, 0)
    }

    fun setBalanceRaw(uuid: UUID, balance: Int) {
        balances[uuid] = balance
    }

    fun giveBalance(player: Player, target: Player, amount: Int) {
        if (amount < 0) {
            player.msg(Config.ARG_IS_NEGATIVE)
            return
        }

        if (getBalance(player.uniqueId) < amount) {
            player.msg(Config.NO_ENOUGH_BALANCE)
            return
        }

        val playerNewBal = getBalance(player.uniqueId) - amount
        val targetNewBal = getBalance(target.uniqueId) + amount

        setBalanceRaw(player.uniqueId, playerNewBal)
        setBalanceRaw(target.uniqueId, targetNewBal)

        player.msg(
            Config.GIVE_SUCCESS
                .replace("{amount}", amount.toString())
                .replace("{target}", target.name)
                .replace("{balance}", playerNewBal.toString())
        )
    }

    fun setBalance(sender: CommandSender, target: Player, value: Int) {
        if (!sender.hasPermission(Config.SET_BALANCE_PERMISSION)) {
            sender.msg(Config.NO_PERMISSION)
            return
        }

        if (value < 0) {
            sender.msg(Config.ARG_IS_NEGATIVE)
            return
        }

        setBalanceRaw(target.uniqueId, value)
        sender.msg(Config.SET_BALANCE_SUCCESS.color())
    }

    fun earnRandomBalance(player: Player) {
        if (cooldownModule.isInCooldown(player)) {
            player.msg(
                Config.EARN_COOLDOWN.replace(
                    "{seconds}",
                    (cooldownModule.getCooldown(player) / 1000).toString()
                )
            )
            return
        }

        val randomValue = this.randomModule.randomValue(Config.EARN_MIN, Config.EARN_MAX)
        val newBalance = getBalance(player.uniqueId) + randomValue
        this.setBalanceRaw(player.uniqueId, newBalance)

        player.msg(
            Config.EARN_SUCCESS
                .replace("{amount}", randomValue.toString())
                .replace("{balance}", newBalance.toString())
        )
    }

}
