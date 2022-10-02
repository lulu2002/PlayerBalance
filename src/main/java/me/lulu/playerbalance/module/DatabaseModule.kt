package me.lulu.playerbalance.module

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.Updates
import me.lulu.playerbalance.Cfg
import java.util.*

interface DatabaseModule {
    fun loadPlayerBalance(uuid: UUID): Int
    suspend fun savePlayerBalance(uuid: UUID, value: Int)
}

class MongoDatabaseModule : DatabaseModule {

    private val mongoClient: MongoClient = MongoClients.create(
        MongoClientSettings.builder()
            .credential(
                MongoCredential.createCredential(
                    Cfg.MONGO_USER,
                    Cfg.MONGO_DB,
                    Cfg.MONGO_PASSWORD.toCharArray()
                )
            )
            .applyToClusterSettings {
                it.hosts(listOf(ServerAddress(Cfg.MONGO_HOST, Cfg.MONGO_PORT.toInt())))
            }
            .build()
    )

    override fun loadPlayerBalance(uuid: UUID): Int {
        return mongoClient.getDatabase(Cfg.MONGO_DB)
            .getCollection(Cfg.MONGO_COLLECTION)
            .find(eq("_id", uuid.toString()))
            .firstOrNull()
            ?.getInteger("balance") ?: 0
    }

    override suspend fun savePlayerBalance(uuid: UUID, value: Int) {
        mongoClient.getDatabase(Cfg.MONGO_DB)
            .getCollection(Cfg.MONGO_COLLECTION)
            .findOneAndUpdate(
                eq("_id", uuid.toString()),
                Updates.set("balance", value),
                FindOneAndUpdateOptions().upsert(true)
            )
    }
}

class MemoryDatabaseModule : DatabaseModule {
    private val playerBalance = mutableMapOf<UUID, Int>()

    override fun loadPlayerBalance(uuid: UUID): Int {
        return playerBalance.getOrDefault(uuid, 0)
    }

    override suspend fun savePlayerBalance(uuid: UUID, value: Int) {
        playerBalance[uuid] = value
    }
}