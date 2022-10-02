package me.lulu.playerbalance

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class BukkitTestBase {

    lateinit var server: ServerMock
    lateinit var plugin: PlayerBalance

    @BeforeEach
    fun setupServerAndPlugin() {
        Testing.TESTING = true

        server = MockBukkit.mock()
        plugin = MockBukkit.load(PlayerBalance::class.java)
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

}