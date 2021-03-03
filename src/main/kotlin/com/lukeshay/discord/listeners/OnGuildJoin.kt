package com.lukeshay.discord.listeners

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.logging.DBLogger
import com.lukeshay.discord.services.GuildConfigService
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OnGuildJoin @Autowired constructor(
    private val guildConfigService: GuildConfigService,
    private val environment: Environment
) :
    ListenerAdapter() {
    override fun onGuildJoin(event: GuildJoinEvent) {
        shouldRun(environment, event)
        logger.info(event, "joined guild - ${event.guild.id}")

        guildConfigService.new(event.guild)

        event.guild.defaultChannel?.sendMessage("Thank you for adding me to your server! Send the message '!help' for information on my commands.")
            ?.queue()
    }

    companion object {
        private val logger = DBLogger("OnGuildJoin")
    }
}
