package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Emoji
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.services.QuoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Quote @Autowired constructor(
    private val quoteService: QuoteService,
    environment: Environment
) :
    Command(
        "quote",
        "I will respond with a quote.",
        true,
        environment
    ) {
    override fun run(event: CommandEvent) {
        event.reply(
            quoteService.findOne(event.guildId)?.format() ?: "A quote could not be found ${Emoji.CRY}"
        )
            .queue()
    }
}
