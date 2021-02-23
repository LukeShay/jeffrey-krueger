package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import com.lukeshay.discord.utils.Rand
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
open class HeyJeff @Autowired constructor(private val rand: Rand) :
    Command("hey jeff", "I will say hi back to you!", false, FeatureStatus.RELEASE) {
    override fun run(event: GuildMessageReceivedEvent) {
        event.message.reply("Hey ${rand.randomVerb()}").queue()
    }
}