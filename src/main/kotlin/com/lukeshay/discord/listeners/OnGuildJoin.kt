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

        val message = guildConfigService.saveOrUpdate(event.guild)?.let {
            "Thank you for adding me to your server! Send the message '!help' for information on my commands."
        }
            ?: "There was an error adding your guild to our database. Please open a ticket here: https://github.com/LukeShay/jeffery-krueger/issues/new?assignees=LukeShay&labels=guild%2C+awaiting+triage&template=guild_ticket.md&title=%5BGUILD%5D+Your+title+here"

        event.guild.defaultChannel?.sendMessage(message)
            ?.queue()
    }

    companion object {
        private val logger = DBLogger("OnGuildJoin")
    }
}
