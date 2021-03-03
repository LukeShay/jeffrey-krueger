package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
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
        event.reply("https://github.com/LukeShay/jeffrey-krueger/issues/new?assignees=LukeShay&labels=feature%2C+awaiting+triage&template=feature_request.md&title=%5BFEATURE%5D+Your+title+here")
            .queue()
    }
}
