package com.lukeshay.discord

import com.lukeshay.discord.commands.Command
import com.lukeshay.discord.listeners.OnGuildMessageReceived
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Bot @Autowired constructor(private val listeners: List<ListenerAdapter>) {
    fun start() {
        val builder =
            JDABuilder.createDefault(System.getenv("DISCORD_TOKEN").orEmpty())

        builder.setAutoReconnect(true)
        builder.setStatus(OnlineStatus.ONLINE)

        for (listener in listeners)
            builder.addEventListeners(listener)

        builder.build()
    }
}