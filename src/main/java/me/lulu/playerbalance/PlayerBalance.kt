package me.lulu.playerbalance

import me.lulu.playerbalance.command.BalanceCommand
import me.lulu.playerbalance.command.EarnCommand
import me.lulu.playerbalance.command.GiveBalanceCommand
import me.lulu.playerbalance.command.SetBalanceCommand
import me.lulu.playerbalance.service.BalanceService
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

class PlayerBalance : JavaPlugin {
    lateinit var balanceService: BalanceService

    override fun onEnable() {
        balanceService = BalanceService()

        getCommand("balance")!!.setExecutor(BalanceCommand(this.balanceService))
        getCommand("givebal")!!.setExecutor(GiveBalanceCommand(this.balanceService))
        getCommand("setbal")!!.setExecutor(SetBalanceCommand(this.balanceService))
        getCommand("earn")!!.setExecutor(EarnCommand(this.balanceService))
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