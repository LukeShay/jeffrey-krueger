package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.bot.domain.CommandEvent
import com.lukeshay.discord.bot.enums.Environment
import com.lukeshay.discord.bot.enums.Issue

class Feature(environment: Environment) :
    Command(
        "feature",
        "I will reply with the feature request link.",
        true,
        environment
    ) {
    override suspend fun run(event: CommandEvent) {
        event.reply(Issue.FEATURE_REQUEST.toString()).queue()
    }
}