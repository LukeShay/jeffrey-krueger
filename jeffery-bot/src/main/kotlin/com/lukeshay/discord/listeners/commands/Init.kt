package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.entities.GuildConfigs
import com.lukeshay.discord.enums.Environment

class Init(environment: Environment) :
    Command(
        "init",
        "I will setup your guild if it hasn't been. You must own the guild to run this command.",
        true,
        environment,
        listOf("setup"),
        true,
    ) {
    override suspend fun run(event: CommandEvent) {
        val message = when {
            GuildConfigs.selectById(event.guild) != null -> "Your guild has already been set up!"
            GuildConfigs.insertOrUpdate(event.guild) == null -> "There was an error saving your guild"
            else -> "Your guild has bee set up!"
        }

        event.reply(message).queue()
    }
}