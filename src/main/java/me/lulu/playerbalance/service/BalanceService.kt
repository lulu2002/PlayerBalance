package me.lulu.playerbalance.service

import org.bukkit.entity.Player
import java.util.*

class BalanceService {

    private val balances = mutableMapOf<UUID, Int>()

    fun getBalance(player: Player): Int {
        return getBalance(player.uniqueId)
    }

    fun getBalance(uuid: UUID): Int {
        return balances.getOrDefault(uuid, 0)
    }

    fun setBalance(uuid: UUID, balance: Int) {
        balances[uuid] = balance
    }

    fun giveBalance(player: Player, target: Player, amount: Int) {
        TODO("Not yet implemented")
    }

}
