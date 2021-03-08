package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.utils.selectOneSingularNounByGuildId

class HeyJeff(environment: Environment) :
    Command(
        "hey jeff",
        "I will say hi back to you!",
        false,
        environment,
        listOf("hi jeff", "hello jeff")
    ) {
    override suspend fun run(event: CommandEvent) {
        event.reply("Hey ${selectOneSingularNounByGuildId(event.guildId)}").queue()
    }
}
