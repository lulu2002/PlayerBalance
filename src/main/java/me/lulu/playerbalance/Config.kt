package me.lulu.playerbalance

object Config {
    val PLAYER_ONLY = "Only player can use this command"
    val BALANCE_OTHER = "{player}'s balance: {balance}"
    val BALANCE_SELF = "Your balance is {balance}"
    val PLAYER_NOT_ONLINE = "{player} is not online."

    val GIVE_BALANCE_USAGE = "&cUsage: /givebalance <player> <amount>"
    val SET_BALANCE_USAGE = "&cUsage: /setbalance <player> <amount>"

    val NO_ENOUGH_BALANCE = "&cYou don't have enough money!"
    val ARG_IS_NEGATIVE = "Balance should not be negative."

    val GIVE_SUCCESS = "Successfully give {player} {amount} balance. Now you have {balance} balance."
}