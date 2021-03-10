package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.bot.domain.CommandEvent
import com.lukeshay.discord.entities.Quotes
import com.lukeshay.discord.bot.enums.Emoji
import com.lukeshay.discord.bot.enums.Environment

class Quote(environment: Environment) :
    Command(
        "quote",
        "I will respond with a quote.",
        true,
        environment
    ) {
    override suspend fun run(event: CommandEvent) {
        event.reply(
            Quotes.selectOneQuoteByGuildId(event.guildId)?.format()
                ?: "A quote could not be found ${Emoji.CRY}"
        ).queue()
    }
}