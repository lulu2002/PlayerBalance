package me.lulu.playerbalance.commands

import me.lulu.playerbalance.BukkitTestBase
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BalanceCommandTest : BukkitTestBase() {

    @Test
    fun withoutArgs_shouldReturnSelfBalance() {
        val player = server.addPlayer()

        player.performCommand("balance")

        assertEquals(player.nextMessage(), "Your balance is 0")
    }

}