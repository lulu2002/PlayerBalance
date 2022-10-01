package me.lulu.playerbalance.commands

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import me.lulu.playerbalance.PlayerBalance
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BalanceCommandTest {

    private lateinit var server: ServerMock
    private lateinit var plugin: PlayerBalance

    @BeforeEach
    fun setup() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(PlayerBalance::class.java)
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun testNothing() {
        
    }

}