package me.lulu.playerbalance.command

import be.seeseemelk.mockbukkit.entity.PlayerMock
import io.mockk.mockk
import io.mockk.verify
import me.lulu.playerbalance.BukkitTestBase
import me.lulu.playerbalance.service.BalanceService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class EarnCommandTest : BukkitTestBase() {
    private lateinit var sender: PlayerMock
    private lateinit var command: EarnCommand
    private lateinit var service: BalanceService

    @BeforeEach
    fun setUp() {
        sender = server.addPlayer()
        service = mockk(relaxed = true)
        command = EarnCommand(service)
    }

    @Test
    fun shouldCallService() {
        execute("earn")
        verify { service.earnRandomBalance(sender) }
    }


    fun assertFail(input: String, failMessage: String) {
        execute(input)
        assertEquals(failMessage, sender.nextMessage())
    }

    fun execute(input: String) {
        val allArgs = input.split(" ")
        command.onCommand(sender, mockk(), allArgs[0], allArgs.subList(1, allArgs.size).toTypedArray())
    }
}