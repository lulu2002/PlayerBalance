package me.lulu.playerbalance.command

import be.seeseemelk.mockbukkit.entity.PlayerMock
import io.mockk.mockk
import io.mockk.verify
import me.lulu.playerbalance.BukkitTestBase
import me.lulu.playerbalance.Cfg
import me.lulu.playerbalance.Config
import me.lulu.playerbalance.extension.color
import me.lulu.playerbalance.service.BalanceService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SetBalanceCommandTest : BukkitTestBase() {

    private lateinit var sender: PlayerMock
    private lateinit var command: SetBalanceCommand
    private lateinit var service: BalanceService

    @BeforeEach
    fun setUp() {
        sender = server.addPlayer()
        service = mockk(relaxed = true)
        command = SetBalanceCommand(service)
    }

    @Test
    fun testInvalidArgs() {
        assertFail("setbalance")
        assertFail("setbalance target")
        assertFail("setbalance target invalid_number")
    }

    @Test
    fun targetNotOnline_shouldFail() {
        assertFail("setbalance target 100", Cfg.PLAYER_NOT_ONLINE.color().replace("{player}", "target"))
    }

    @Test
    fun argsValid_shouldCallService() {
        val target = server.addPlayer("target")
        execute("setbalance target 100")

        verify { service.setBalance(sender, target, 100) }
    }

    fun assertFail(input: String, failMessage: String = Cfg.SET_BALANCE_USAGE.color()) {
        execute(input)
        assertEquals(failMessage, sender.nextMessage())
    }

    fun execute(input: String) {
        val allArgs = input.split(" ")
        command.onCommand(sender, mockk(), allArgs[0], allArgs.subList(1, allArgs.size).toTypedArray())
    }

}