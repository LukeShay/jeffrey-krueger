package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.enums.Emoji
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.FeatureStatus
import com.lukeshay.discord.services.QuoteService
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
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
        FeatureStatus.RELEASE,
        environment
    ) {
    override fun run(event: GuildMessageReceivedEvent) {
        event.message.reply(
            quoteService.findOne()?.format() ?: "A quote could not be found ${Emoji.CRY}"
        )
            .queue()
    }
}
