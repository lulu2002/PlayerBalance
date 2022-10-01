package me.lulu.playerbalance.service

import org.bukkit.entity.Player
import java.util.*

class BalanceService {

    private val balances = mutableMapOf<UUID, Double>()

    fun getBalance(player: Player): Double {
        return getBalance(player.uniqueId)
    }

    fun getBalance(uuid: UUID): Double {
        return balances.getOrDefault(uuid, 0.0)
    }

    fun setBalance(uuid: UUID, balance: Double) {
        balances[uuid] = balance
    }

}
