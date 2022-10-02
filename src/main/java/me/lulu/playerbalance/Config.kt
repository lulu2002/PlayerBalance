package me.lulu.playerbalance

object Config {
    val PLAYER_ONLY = "Only player can use this command"
    val BALANCE_OTHER = "{player}'s balance: {balance}"
    val BALANCE_SELF = "Your balance is {balance}"
    val PLAYER_NOT_ONLINE = "{player} is not online."

    val SET_BALANCE_PERMISSION = "playerbalance.admin"

    val GIVE_BALANCE_USAGE = "&cUsage: /givebalance <player> <amount>"
    val SET_BALANCE_USAGE = "&cUsage: /setbalance <player> <amount>"

    val NO_PERMISSION = "&cYou don't have permission to do that."
    val NO_ENOUGH_BALANCE = "&cYou don't have enough money!"
    val ARG_IS_NEGATIVE = "Balance should not be negative."
    val EARN_COOLDOWN = "&cYou have to wait {seconds} seconds to earn money again."
    val CANT_GIVE_SELF_BALANCE = "You can't give yourself balance!"

    val GIVE_SUCCESS = "Successfully give {player} {amount} balance. Now you have {balance} balance."
    val SET_BALANCE_SUCCESS = "Successfully set {player}'s balance to {amount}."
    val EARN_SUCCESS = "You earned {amount} balance. Now you have {balance} balance."

    val EARN_MIN: Int = 1
    val EARN_MAX: Int = 5
    val EARN_CD: Long = 60 * 1000
}