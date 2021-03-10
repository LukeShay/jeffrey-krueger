package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.bot.domain.CommandEvent
import com.lukeshay.discord.bot.enums.Environment
import com.lukeshay.discord.bot.enums.Issue

class Bug(environment: Environment) :
    Command(
        "bug",
        "I will reply with the bug report link.",
        true,
        environment
    ) {
    override suspend fun run(event: CommandEvent) {
        event.reply(Issue.BUG_REPORT.toString()).queue()
    }
}