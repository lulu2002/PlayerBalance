package me.lulu.playerbalance.module

import java.util.UUID

interface DatabaseModule {
    fun loadPlayerBalance(uuid: UUID): Int
    fun savePlayerBalance(uuid: UUID, value: Int)
}

class MongoDatabaseModule : DatabaseModule {
    override fun loadPlayerBalance(uuid: UUID): Int {
        return 0
    }

    override fun savePlayerBalance(uuid: UUID, value: Int) {

    }
}