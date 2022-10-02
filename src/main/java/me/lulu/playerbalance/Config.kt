package me.lulu.playerbalance

import kotlinx.serialization.Serializable
import java.util.StringJoiner

@Serializable
data class Config(
    val PLAYER_ONLY: String = "Only player can use this command",
    val BALANCE_OTHER: String = "{player}'s balance: {balance}",
    val BALANCE_SELF: String = "Your balance is {balance}",
    val PLAYER_NOT_ONLINE: String = "{player} is not online.",

    val SET_BALANCE_PERMISSION: String = "playerbalance.admin",

    val GIVE_BALANCE_USAGE: String = "&cUsage: /givebalance <player> <amount>",
    val SET_BALANCE_USAGE: String = "&cUsage: /setbalance <player> <amount>",

    val NO_PERMISSION: String = "&cYou don't have permission to do that.",
    val NO_ENOUGH_BALANCE: String = "&cYou don't have enough money!",
    val ARG_IS_NEGATIVE: String = "Balance should not be negative.",
    val EARN_COOLDOWN: String = "&cYou have to wait {seconds} seconds to earn money again.",
    val CANT_GIVE_SELF_BALANCE: String = "You can't give yourself balance!",

    val GIVE_SUCCESS: String = "Successfully give {player} {amount} balance. Now you have {balance} balance.",
    val SET_BALANCE_SUCCESS: String = "Successfully set {player}'s balance to {amount}.",
    val EARN_SUCCESS: String = "You earned {amount} balance. Now you have {balance} balance.",

    val EARN_MIN: Int = 1,
    val EARN_MAX: Int = 5,
    val EARN_CD: Long = 60 * 1000,

    var MONGO_HOST: String = "localhost",
    var MONGO_PORT: String = "27017",
    var MONGO_DB: String = "playerbalance",
    var MONGO_COLLECTION: String = "playerbalance",
    var MONGO_USER: String = "user",
    var MONGO_PASSWORD: String = "password"
)