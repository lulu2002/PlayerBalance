package me.lulu.playerbalance.command

import be.seeseemelk.mockbukkit.entity.PlayerMock
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import me.lulu.playerbalance.BukkitTestBase
import me.lulu.playerbalance.Cfg
import me.lulu.playerbalance.Config
import me.lulu.playerbalance.command.GiveBalanceCommand
import me.lulu.playerbalance.extension.color
import me.lulu.playerbalance.service.BalanceService
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GiveBalanceCommandTest : BukkitTestBase() {


    @Test
    fun failCases() {
        val sender = server.addPlayer("sender")

        assertFail(sender, "givebal")
        assertFail(sender, "givebal target")
        assertFail(sender, "givebal target not_number")
    }

    @Test
    fun valid_shouldCallService() {
        val sender = server.addPlayer("sender")
        val target = server.addPlayer("target")
        val amount = 100

        val service = mockk<BalanceService>(relaxed = true)
        val command = GiveBalanceCommand(service)

        command.onCommand(sender, mockk(), "givebal", arrayOf(target.name, amount.toString()))

        verify { service.giveBalance(sender, target, amount) }
    }

    fun assertFail(sender: PlayerMock, command: String) {
        sender.performCommand(command)
        assertEquals(Cfg.GIVE_BALANCE_USAGE.color(), sender.nextMessage())
    }

//    @Test
//    fun targetNotOnline_shouldFail() {
//        val sender = server.addPlayer("sender")
//
//        sender.performCommand( "givebalance target 100")
//
//        assertEquals(sender.nextMessage(), Cfg.PLAYER_NOT_ONLINE.replace("{player}", "target"))
//    }
//

}