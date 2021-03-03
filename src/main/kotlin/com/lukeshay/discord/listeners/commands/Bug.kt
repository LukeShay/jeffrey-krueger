package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
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
        event.reply("https://github.com/LukeShay/jeffrey-krueger/issues/new?assignees=LukeShay&labels=bug%2C+awaiting+triage&template=bug_report.md&title=%5BBUG%5D+Your+title+here")
            .queue()
    }
}
