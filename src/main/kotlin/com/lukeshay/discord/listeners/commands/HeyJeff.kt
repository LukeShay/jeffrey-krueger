package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.services.WordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HeyJeff @Autowired constructor(
    private val wordService: WordService,
    environment: Environment
) :
    Command(
        "hey jeff",
        "I will say hi back to you!",
        false,
        environment,
        listOf("hi jeff", "hello jeff")
    ) {
    override fun run(event: CommandEvent) {
        event.reply("Hey ${wordService.randomSingularNoun()}").queue()
    }
}
