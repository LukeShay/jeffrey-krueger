package com.lukeshay.discord.listeners

import com.lukeshay.discord.commands.Command
import com.lukeshay.discord.listeners.exceptions.NoCommandRuntimeException
import com.lukeshay.discord.logging.DBLogger
import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.entities.GuildChannel
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OnGuildMessageReceived @Autowired constructor(private val commands: List<Command>) :
    ListenerAdapter() {

    companion object {
        private val logger = DBLogger("OnGuildMessageReceived")
    }

    init {
        logger.info("Avaliable commands:")
        for (command in commands) {
            logger.info("    ${command.command}")
        }
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.author.isBot) return

        logger.info(event, "message - ${event.author.name}: ${event.message.contentRaw}")

        for (command in commands) {
            if (event.message.contentRaw.startsWith(
                    command.command,
                    ignoreCase = true
                )
            ) {
                if (command.status.isAllowed(getCategoryOfChannel(event))) {
                    logger.info("running command ${command.command}")
                    command.run(event)
                } else {
                    logger.info("would have ran command ${command.command}")
                }

                return
            }

        }

        throw NoCommandRuntimeException("no command found for message - ${event.message.contentRaw}")
    }

    private fun getCategoryOfChannel(event: GuildMessageReceivedEvent): Category? {
        for (category in event.jda.categories) {
            for (ch in category.channels) {
                if (ch.id == event.channel.id) {
                    return category
                }
            }
        }

        return null
    }
}