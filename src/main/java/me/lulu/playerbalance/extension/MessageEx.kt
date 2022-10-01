package me.lulu.playerbalance.extension

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

fun CommandSender.msg(message: String) = this.sendMessage(message.color())
fun String.color() = ChatColor.translateAlternateColorCodes('&', this)