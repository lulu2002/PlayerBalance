package me.lulu.playerbalance.module

import org.bukkit.entity.Player

interface CooldownModule {
    fun getCooldown(player: Player): Long
    fun isInCooldown(player: Player): Boolean
    fun setCooldown(player: Player, time: Long)
}

class CooldownModuleImpl : CooldownModule {

    private val cdUntil = mutableMapOf<Player, Long>()

    override fun getCooldown(player: Player): Long {
        val currentTime = System.currentTimeMillis()
        val cdTime = cdUntil[player] ?: currentTime
        return cdTime - currentTime
    }

    override fun isInCooldown(player: Player): Boolean {
        return getCooldown(player) > 0
    }

    override fun setCooldown(player: Player, time: Long) {
        cdUntil[player] = System.currentTimeMillis() + time
    }

}