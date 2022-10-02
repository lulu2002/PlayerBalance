package me.lulu.playerbalance.service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.lulu.playerbalance.Cfg
import me.lulu.playerbalance.extension.color
import me.lulu.playerbalance.extension.msg
import me.lulu.playerbalance.module.CooldownModule
import me.lulu.playerbalance.module.DatabaseModule
import me.lulu.playerbalance.module.RandomModule
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Future


class BalanceService(
    private val cooldownModule: CooldownModule,
    private val randomModule: RandomModule,
    private val databaseModule: DatabaseModule
) {

    private val balances = mutableMapOf<UUID, Int>()
    private val executor = Executors.newSingleThreadExecutor()

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
            player.msg(Cfg.ARG_IS_NEGATIVE)
            return
        }

        if (getBalance(player.uniqueId) < amount) {
            player.msg(Cfg.NO_ENOUGH_BALANCE)
            return
        }

        if (player.uniqueId == target.uniqueId) {
            player.msg(Cfg.CANT_GIVE_SELF_BALANCE)
            return
        }

        val playerNewBal = getBalance(player.uniqueId) - amount
        val targetNewBal = getBalance(target.uniqueId) + amount

        setBalanceRaw(player.uniqueId, playerNewBal)
        setBalanceRaw(target.uniqueId, targetNewBal)

        player.msg(
            Cfg.GIVE_SUCCESS
                .replace("{amount}", amount.toString())
                .replace("{target}", target.name)
                .replace("{balance}", playerNewBal.toString())
        )

    }

    fun setBalance(sender: CommandSender, target: Player, value: Int) {
        if (!sender.hasPermission(Cfg.SET_BALANCE_PERMISSION)) {
            sender.msg(Cfg.NO_PERMISSION)
            return
        }

        if (value < 0) {
            sender.msg(Cfg.ARG_IS_NEGATIVE)
            return
        }

        setBalanceRaw(target.uniqueId, value)
        sender.msg(Cfg.SET_BALANCE_SUCCESS.color())
    }

    fun earnRandomBalance(player: Player) {
        if (cooldownModule.isInCooldown(player)) {
            player.msg(
                Cfg.EARN_COOLDOWN.replace(
                    "{seconds}",
                    (cooldownModule.getCooldown(player) / 1000).toString()
                )
            )
            return
        }

        val randomValue = this.randomModule.randomValue(Cfg.EARN_MIN, Cfg.EARN_MAX)
        val newBalance = getBalance(player.uniqueId) + randomValue
        this.setBalanceRaw(player.uniqueId, newBalance)

        player.msg(
            Cfg.EARN_SUCCESS
                .replace("{amount}", randomValue.toString())
                .replace("{balance}", newBalance.toString())
        )

        cooldownModule.setCooldown(player, Cfg.EARN_CD)
    }

    fun loadBalanceData(uuid: UUID) {
        val balance = this.databaseModule.loadPlayerBalance(uuid)
        setBalanceRaw(uuid, balance)
    }

    fun saveBalanceData(uuid: UUID) {
        GlobalScope.launch {
            databaseModule.savePlayerBalance(uuid, getBalance(uuid))
            balances.remove(uuid)
        }
    }

}
