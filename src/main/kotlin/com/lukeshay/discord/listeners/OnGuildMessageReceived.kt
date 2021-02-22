package com.lukeshay.discord.listeners

import com.lukeshay.discord.Bot
import com.lukeshay.discord.commands.Command
import com.lukeshay.discord.info
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OnGuildMessageReceived @Autowired constructor(private val commands: List<Command>) :
    ListenerAdapter() {

    init {
        println("Avaliable commands:")
        for (command in commands) {
            println("    !${command.command}")
        }
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.author.isBot) return

        info(event, "message - ${event.author.name}: ${event.message.contentRaw}")

        for (command in commands) {
            if (command.handle(event)) {
                return
            }

        }

    }
}