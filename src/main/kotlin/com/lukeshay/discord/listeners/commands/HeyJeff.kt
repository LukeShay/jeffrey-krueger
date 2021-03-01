package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.FeatureStatus
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.lukeshay.discord.services.WordService

@Component
class HeyJeff @Autowired constructor(
    private val wordService: WordService,
    environment: Environment
) :
    Command(
        "hey jeff",
        "I will say hi back to you!",
        false,
        FeatureStatus.RELEASE,
        environment,
        listOf("hi jeff", "hello jeff")
    ) {
    override fun run(event: GuildMessageReceivedEvent) {
        event.message.reply("Hey ${wordService.randomSingularNoun()}").queue()
    }
}
