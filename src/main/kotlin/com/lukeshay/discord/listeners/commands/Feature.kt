package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.Issue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Feature @Autowired constructor(environment: Environment) :
    Command(
        "feature",
        "I will reply with the feature request link.",
        true,
        environment
    ) {
    override fun run(event: CommandEvent) {
        event.reply(Issue.FEATURE_REQUEST.toString()).queue()
    }
}
