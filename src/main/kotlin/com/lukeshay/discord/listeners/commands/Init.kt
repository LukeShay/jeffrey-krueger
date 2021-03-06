package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.utils.insertOrUpdateGuildConfig
import com.lukeshay.discord.utils.selectGuildConfigById
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Init @Autowired constructor(environment: Environment) :
    Command(
        "init",
        "I will setup your guild if it hasn't been. You must own the guild to run this command.",
        true,
        environment,
        listOf("setup"),
        true,
    ) {
    override fun run(event: CommandEvent) {
        val message = if (selectGuildConfigById(event.guild) != null) {
            "Your guild has already been set up!"
        } else if (insertOrUpdateGuildConfig(event.guild) == null) {
            "There was an error saving your guild"
        } else {
            "Your guild has bee set up!"
        }

        event.reply(message).queue()
    }
}
