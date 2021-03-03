package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.Issue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Bug @Autowired constructor(environment: Environment) :
    Command(
        "bug",
        "I will reply with the bug report link.",
        true,
        environment
    ) {
    override fun run(event: CommandEvent) {
        event.reply(Issue.BUG_REPORT.toString()).queue()
    }
}
