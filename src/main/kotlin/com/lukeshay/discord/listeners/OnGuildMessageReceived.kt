package com.lukeshay.discord.listeners

import com.lukeshay.discord.commands.Command
import com.lukeshay.discord.commands.Help
import com.lukeshay.discord.commands.Options
import com.lukeshay.discord.listeners.exceptions.NoCommandRuntimeException
import com.lukeshay.discord.logging.DBLogger
import net.dv8tion.jda.api.entities.Category
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OnGuildMessageReceived @Autowired constructor(private val commands: MutableList<Command>) :
    ListenerAdapter() {

    companion object {
        private val logger = DBLogger("OnGuildMessageReceived")
    }

    init {
        var cmds = ""

        val commandsCopy = commands.toList()

        commands.add(Help(commandsCopy))
        commands.add(Options(commandsCopy))

        for (command in commands) {
            cmds += "\"${command.command}\", "
        }


        logger.info("avaliable commands - [${cmds.substring(0, cmds.length - 2)}]")
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
                if (isAllowed(command, event)) {
                    logger.info(event, "running command - ${command.command}")
                    command.run(event)
                } else {
                    logger.info(event, "would have ran command - ${command.command}")
                }

                return
            }
        }

        logger.info(event, "no command found for message - ${event.message.contentRaw}")

        throw NoCommandRuntimeException("no command found for message - ${event.message.contentRaw}")
    }

    private fun isAllowed(command: Command, event: GuildMessageReceivedEvent): Boolean {
        return command.status.isAllowed(getCategoryOfChannel(event))
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