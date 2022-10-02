package me.lulu.playerbalance.module

import org.bukkit.entity.Player

interface CooldownModule {
    fun getCooldown(player: Player): Long
    fun isInCooldown(player: Player): Boolean
    fun setCooldown(player: Player, time: Long)
}

class CooldownModuleImpl : CooldownModule {

    private val cdMillis = mutableMapOf<Player, Long>()

    override fun getCooldown(player: Player): Long {
        val currentTime = System.currentTimeMillis()
        val cdTime = cdMillis[player] ?: return currentTime
        return currentTime - cdTime
    }

    override fun isInCooldown(player: Player): Boolean {
        return getCooldown(player) <= 0
    }

    override fun setCooldown(player: Player, time: Long) {
        cdMillis[player] = System.currentTimeMillis() + time
    }

}