package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Emoji
import com.lukeshay.discord.enums.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Ping @Autowired constructor(environment: Environment) :
    Command("ping", "I will reply with Pong.", true, environment) {
    override fun run(event: CommandEvent) {
        event.sendMessage("Pong ${Emoji.PING_PONG}").queue()
    }
}
