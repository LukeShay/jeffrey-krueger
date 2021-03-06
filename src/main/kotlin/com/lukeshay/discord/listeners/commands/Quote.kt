package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Emoji
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.utils.formatQuote
import com.lukeshay.discord.utils.selectOneQuoteByGuildId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Quote @Autowired constructor(environment: Environment) :
    Command(
        "quote",
        "I will respond with a quote.",
        true,
        environment
    ) {
    override fun run(event: CommandEvent) {
        event.reply(
            selectOneQuoteByGuildId(event.guildId)?.let { formatQuote(it) }
                ?: "A quote could not be found ${Emoji.CRY}"
        ).queue()
    }
}
