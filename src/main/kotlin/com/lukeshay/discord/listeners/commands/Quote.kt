package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Emoji
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.utils.formatQuote
import com.lukeshay.discord.utils.selectOneQuoteByGuildId

class Quote(environment: Environment) :
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
