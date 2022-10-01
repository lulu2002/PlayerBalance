package me.lulu.playerbalance.service

import me.lulu.playerbalance.BukkitTestBase
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals

internal class BalanceServiceTest : BukkitTestBase() {

    private val service = BalanceService()

    @Nested
    inner class GetBalance {

        @Test
        fun playerNotExist_shouldReturnsZero() {
            val balance = service.getBalance(UUID.randomUUID())
            assertEquals(balance, 0)
        }

        @Test
        fun playerExist_shouldReturnsBalance() {
            val uuid = UUID.randomUUID()
            service.setBalance(uuid, 100)

            val balance = service.getBalance(uuid)
            assertEquals(balance, 100)
        }

    }

}