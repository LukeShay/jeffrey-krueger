package com.lukeshay.discord.listeners

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.listeners.commands.Command
import com.lukeshay.discord.listeners.commands.Help
import com.lukeshay.discord.listeners.exceptions.NoCommandRuntimeException
import com.lukeshay.discord.logging.DBLogger
import com.lukeshay.discord.utils.ListUtils
import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OnGuildMessageReceived @Autowired constructor(
    cmds: MutableList<Command>,
    private val environment: Environment
) :
    ListenerAdapter() {

    private lateinit var commands: List<Command>

    init {
        cmds.add(Help(cmds.toList(), environment))
        commands = cmds.toList()

        logger.info("available commands -\n\n${ListUtils.toString(commands, ",", "    ", true)}\n")
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if (event.author.isBot || (
            environment != Environment.PRODUCTION && !event.message.contentRaw.startsWith(
                    environment.toString().toLowerCase()
                )
            )
        ) {
            return
        }

        logger.info(event, "message - ${event.author.name}: ${event.message.contentRaw}")

        val category = getCategoryOfChannel(
            event
        )
        val command = findCommand(event)

        if (command != null) {
            if (!environment.isAllowed(
                    category
                )
            ) {
                logger.info(
                    event,
                    "environment $environment not allowed in this category - ${category?.id}"
                )
            } else {
                logger.info(event, "running command - ${command.command}")
                command.run(event)
            }
        } else {
            logger.info(event, "no command found for message - ${event.message.contentRaw}")

            throw NoCommandRuntimeException("no command found for message - ${event.message.contentRaw}")
        }
    }

    private fun findCommand(event: GuildMessageReceivedEvent): Command? {
        return try {
            commands.first { command ->
                command.matches(
                    event.message.contentRaw.removePrefix(
                        "${
                        environment.toString().toLowerCase()
                        } "
                    )
                ) && command.isAllowed(
                    getCategoryOfChannel(
                        event
                    )
                )
            }
        } catch (e: NoSuchElementException) {
            null
        }
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

    companion object {
        private val logger = DBLogger("OnGuildMessageReceived")
    }
}
