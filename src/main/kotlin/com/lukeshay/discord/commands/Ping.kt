package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
open class Ping : Command("ping", "I will reply with Pong.", true, FeatureStatus.RELEASE) {
    override fun run(event: GuildMessageReceivedEvent) {
        event.channel.sendMessage("Pong :ping_pong:").queue()
    }
}
