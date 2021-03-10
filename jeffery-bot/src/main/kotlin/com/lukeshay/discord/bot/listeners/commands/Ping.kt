package com.lukeshay.discord.bot.listeners.commands

import com.lukeshay.discord.bot.domain.CommandEvent
import com.lukeshay.discord.bot.enums.Emoji
import com.lukeshay.discord.bot.enums.Environment

class Ping(environment: Environment) :
    Command("ping", "I will reply with Pong.", true, environment) {
    override suspend fun run(event: CommandEvent) {
        event.sendMessage("Pong ${Emoji.PING_PONG}").queue()
    }
}