package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import com.lukeshay.discord.utils.Rand
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
open class Hey @Autowired constructor(private val rand: Rand) :
    Command("hey", FeatureStatus.RELEASE) {
    override fun run(event: GuildMessageReceivedEvent) {
        if (event.message.contentRaw.startsWith("hey jeff", ignoreCase = true))
        event.message.reply("Hey ${rand.randomVerb()}").queue()
    }
}