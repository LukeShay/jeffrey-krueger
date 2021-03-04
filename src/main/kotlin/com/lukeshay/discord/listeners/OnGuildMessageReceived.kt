package com.lukeshay.discord.listeners

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.listeners.commands.Command
import com.lukeshay.discord.listeners.commands.Help
import com.lukeshay.discord.listeners.exceptions.NoCommandRuntimeException
import com.lukeshay.discord.logging.createLogger
import com.lukeshay.discord.services.GuildConfigService
import com.lukeshay.discord.utils.ListUtils
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OnGuildMessageReceived @Autowired constructor(
    cmds: MutableList<Command>,
    private val environment: Environment,
    private val guildConfigService: GuildConfigService
) :
    ListenerAdapter() {

    private lateinit var commands: List<Command>

    init {
        cmds.add(Help(cmds.toList(), environment, guildConfigService))
        commands = cmds.toList()

        logger.info("available commands -\n\n${ListUtils.toString(commands, ",", "    ", true)}\n")
    }

    override fun onGuildMessageReceived(e: GuildMessageReceivedEvent) {
        shouldRun(environment, e)
        val event = CommandEvent(e, environment)

        if (event.isBot || (
            environment != Environment.PRODUCTION && !event.event.message.contentRaw.startsWith(
                    environment.toString().toLowerCase()
                )
            )
        ) {
            return
        }

        logger.fine("${event.guildId}, ${event.authorId} | message - ${event.author.name}: ${event.contentRaw}")

        val command = findCommand(e)

        if (command != null) {
            if (!command.adminOnly || guildConfigService.isAdmin(
                    event.guild,
                    event.authorAsMember
                )
            ) {
                logger.fine("${event.guildId}, ${event.authorId} | running command - ${command.command}")
                command.run(event)
            } else {
                logger.fine("${event.guildId}, ${event.authorId} | unauthorized command - ${command.command}")
                event.reply("You must be an admin to run that command XD").queue()
            }
        } else {
            logger.fine("${event.guildId}, ${event.authorId} | no command found for message - ${event.contentRaw}")

            throw NoCommandRuntimeException("no command found for message - ${event.contentRaw}")
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
                )
            }
        } catch (e: NoSuchElementException) {
            null
        }
    }

    companion object {
        private val logger = createLogger("OnGuildMessageReceived")
    }
}
