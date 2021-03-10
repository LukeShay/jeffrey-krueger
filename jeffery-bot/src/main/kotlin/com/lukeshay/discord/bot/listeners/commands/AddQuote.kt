package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.bot.domain.CommandEvent
import com.lukeshay.discord.entities.Quotes
import com.lukeshay.discord.bot.enums.Emoji
import com.lukeshay.discord.bot.enums.Environment

class AddQuote(environment: Environment) : Command(
    cmd = "add-quote",
    desc = "Adds the quote to the database. Usage: \"Quote\" \"author\" \"date\"",
    leader = true,
    adminOnly = true,
    environment = environment,
) {
    override suspend fun run(event: CommandEvent) {
        val splitMessage = quoteRegex.matchEntire(event.contentRaw)!!.groups

        val quote = splitMessage["quote"]
        val author = splitMessage["author"]
        val date = splitMessage["date"]

        if (quote == null) {
            event.reply("A quote is required")
        } else {
            val message =
                Quotes.insertQuote(
                    event.guildId,
                    quote.value,
                    author?.value,
                    date?.value,
                )
                    ?.let {
                        "Your quote has been saved!"
                    } ?: "There was an error saving your quote ${Emoji.CRY}"

            event.reply(message).queue()
        }
    }

    companion object {
        private val quoteRegex =
            """!add-quote "(?<quote>[^"]+)"( "(?<author>[^"]*)"( "(?<date>[^"]+)")?)?""".toRegex()
    }
}
