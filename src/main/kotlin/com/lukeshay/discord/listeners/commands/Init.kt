package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.services.GuildConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Init @Autowired constructor(
    private val guildConfigService: GuildConfigService,
    environment: Environment
) :
    Command(
        "init",
        "I will setup your guild if it hasn't been. You must own the guild to run this command.",
        true,
        environment,
        listOf("setup"),
        false
    ) {
    override fun run(event: CommandEvent) {
        val message = if (event.authorAsMember == null || !event.authorAsMember.isOwner) {
            "You are not allowed to run this command!"
        } else if (guildConfigService.new(event.guild) == null) {
            "Your guild has already been set up!"
        } else {
            "Your guild has bee set up!"
        }

        event.reply(message).queue()
    }
}
