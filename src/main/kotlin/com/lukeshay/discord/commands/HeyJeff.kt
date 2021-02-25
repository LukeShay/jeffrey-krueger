package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import com.lukeshay.discord.services.WordService
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HeyJeff @Autowired constructor(private val wordService: WordService) :
    Command("hey jeff", "I will say hi back to you!", false, FeatureStatus.RELEASE, listOf("hi jeff", "hello jeff")) {
    override fun run(event: GuildMessageReceivedEvent) {
        event.message.reply("Hey ${wordService.randomSingularVerb()}").queue()
    }
}
