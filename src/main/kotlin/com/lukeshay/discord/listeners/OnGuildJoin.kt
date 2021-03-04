package com.lukeshay.discord.listeners

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.Issue
import com.lukeshay.discord.logging.createLogger
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
        logger.info("joined guild - ${event.guild.id}")

        val message = if (guildConfigService.saveOrUpdate(event.guild) != null) {
            "Thank you for adding me to your server! Send the message '!help' for information on my commands."
        } else {
            logger.error("there was an error adding guild ${event.guild.id} to the database")
            "There was an error adding your guild to our database. Please open a ticket here: ${Issue.GUILD_TICKET}"
        }

        event.guild.defaultChannel?.sendMessage(message)?.queue()
    }

    companion object {
        private val logger = createLogger(OnGuildJoin::class.java)
    }
}
