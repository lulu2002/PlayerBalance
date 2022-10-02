package me.lulu.playerbalance

import me.lulu.playerbalance.command.BalanceCommand
import me.lulu.playerbalance.command.EarnCommand
import me.lulu.playerbalance.command.GiveBalanceCommand
import me.lulu.playerbalance.command.SetBalanceCommand
import me.lulu.playerbalance.listener.JoinQuitListener
import me.lulu.playerbalance.module.*
import me.lulu.playerbalance.service.BalanceService
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File


lateinit var Cfg: Config

class PlayerBalance : JavaPlugin {
    lateinit var balanceService: BalanceService

    override fun onEnable() {
        Cfg = loadConfig()

        balanceService = BalanceService(
            cooldownModule = CooldownModuleImpl(),
            randomModule = RandomModuleImpl(),
            databaseModule = getDatabaseModule()
        )

        getCommand("balance")!!.setExecutor(BalanceCommand(this.balanceService))
        getCommand("givebal")!!.setExecutor(GiveBalanceCommand(this.balanceService))
        getCommand("setbal")!!.setExecutor(SetBalanceCommand(this.balanceService))
        getCommand("earn")!!.setExecutor(EarnCommand(this.balanceService))

        if (!Testing.TESTING) {
            Bukkit.getPluginManager().registerEvents(JoinQuitListener(this.balanceService), this)
        }
    }

    private fun getDatabaseModule(): DatabaseModule {
        if (Testing.TESTING)
            return MemoryDatabaseModule()
        else
            return MongoDatabaseModule()
    }

    private fun loadConfig(): Config {
        val cfg = Config()

        cfg.MONGO_HOST = config.getStringOrSet("mongo.host", cfg.MONGO_HOST)
        cfg.MONGO_PORT = config.getStringOrSet("mongo.port", cfg.MONGO_PORT)
        cfg.MONGO_DB = config.getStringOrSet("mongo.database", cfg.MONGO_DB)
        cfg.MONGO_COLLECTION = config.getStringOrSet("mongo.collection", cfg.MONGO_COLLECTION)
        cfg.MONGO_USER = config.getStringOrSet("mongo.user", cfg.MONGO_USER)
        cfg.MONGO_PASSWORD = config.getStringOrSet("mongo.password", cfg.MONGO_PASSWORD)

        saveConfig()

        return cfg
    }

    fun FileConfiguration.getStringOrSet(path: String, default: String): String {
        val str = this.getString(path) ?: kotlin.run { this.set(path, default); default }
        return str
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    constructor() : super()
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File) : super(
        loader,
        description,
        dataFolder,
        file
    )

}