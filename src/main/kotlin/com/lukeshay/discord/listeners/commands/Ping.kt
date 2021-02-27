package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.FeatureStatus
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Ping @Autowired constructor(environment: Environment) :
    Command("ping", "I will reply with Pong.", true, FeatureStatus.RELEASE, environment) {
    override fun run(event: GuildMessageReceivedEvent) {
        event.channel.sendMessage("Pong :ping_pong:").queue()
    }
}
