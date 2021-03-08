package com.lukeshay.discord.listeners

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.listeners.commands.Command
import com.lukeshay.discord.listeners.commands.Help
import com.lukeshay.discord.listeners.exceptions.NoCommandRuntimeException
import com.lukeshay.discord.logging.createLogger
import com.lukeshay.discord.utils.isAdmin
import com.lukeshay.discord.utils.listToString
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.jetbrains.exposed.sql.transactions.transaction

class OnGuildMessageReceived(
    cmds: MutableList<Command>,
    private val environment: Environment,
) :
    ListenerAdapter() {

    private var commands: List<Command>

    init {
        cmds.add(Help(cmds.toList(), environment))
        commands = cmds.toList()

        logger.info("available commands -\n\n${listToString(commands, ",", "    ", true)}\n")
    }

    override fun onGuildMessageReceived(e: GuildMessageReceivedEvent) {
        shouldRun(environment, e)
        val event = CommandEvent(e, environment)

        if (!event.shouldRun) {
            logger.info("${event.guildId}, ${event.authorId} | message is from a bot or not for this env")
            return
        }

        logger.info("${event.guildId}, ${event.authorId} | message - ${event.author.name}: ${event.contentRaw}")

        val command = findCommand(e)

        if (command != null) {
            if (!command.adminOnly || event.authorAsMember?.isOwner == true || isAdmin(
                    event.guild,
                    event.authorAsMember
                )
            ) {
                logger.info("${event.guildId}, ${event.authorId} | running command - ${command.command}")
                transaction {
                    runBlocking {
                        command.run(event)
                    }
                }
            } else {
                logger.info("${event.guildId}, ${event.authorId} | unauthorized command - ${command.command}")
                event.reply("You must be an admin to run that command XD").queue()
            }
        } else {
            logger.info("${event.guildId}, ${event.authorId} | no command found for message - ${event.contentRaw}")

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
        private val logger = createLogger(OnGuildMessageReceived::class.java)
    }
}
