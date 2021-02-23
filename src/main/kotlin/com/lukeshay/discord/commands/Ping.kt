package com.lukeshay.discord.commands

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
open class Ping : Command("!ping") {
    override fun run(event: GuildMessageReceivedEvent) {
        event.channel.sendMessage("Pong :ping_pong:").queue()
    }
}