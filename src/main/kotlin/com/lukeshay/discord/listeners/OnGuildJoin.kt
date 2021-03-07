package com.lukeshay.discord.listeners

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.Issue
import com.lukeshay.discord.logging.createLogger
import com.lukeshay.discord.utils.insertOrUpdateGuildConfig
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.jetbrains.exposed.sql.transactions.transaction

class OnGuildJoin(private val environment: Environment) :
    ListenerAdapter() {
    override fun onGuildJoin(event: GuildJoinEvent) {
        shouldRun(environment, event)
        logger.info("joined guild - ${event.guild.id}")

        transaction {
            val message = if (insertOrUpdateGuildConfig(event.guild) != null) {
                "Thank you for adding me to your server! Send the message '!help' for information on my commands."
            } else {
                logger.error("there was an error adding guild ${event.guild.id} to the database")
                "There was an error adding your guild to our database. Please open a ticket here: ${Issue.GUILD_TICKET}"
            }

            event.guild.defaultChannel?.sendMessage(message)?.queue()
        }
    }

    companion object {
        private val logger = createLogger(OnGuildJoin::class.java)
    }
}
