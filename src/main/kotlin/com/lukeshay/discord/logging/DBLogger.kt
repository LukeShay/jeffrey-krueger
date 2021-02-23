package com.lukeshay.discord.logging

import net.dv8tion.jda.api.events.guild.GenericGuildEvent
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger

class DBLogger(clazz: String) {

    private var logger: Logger = Logger.getLogger(clazz)

    init {
        logger.level = Level.ALL
        logger.useParentHandlers = false
        logger.handlers.forEach { handler -> logger.removeHandler(handler) }

        val consoleHandler = ConsoleHandler()

        consoleHandler.formatter = LogFormatter()

        logger.addHandler(consoleHandler)
    }

    fun finest(event: GenericGuildEvent, message: String) {
        finest("${event.guild.name} | $message")
    }

    fun finest(message: String) {
        logger.finest(message)
    }

    fun finer(event: GenericGuildEvent, message: String) {
        finer("${event.guild.name} | $message")
    }

    fun finer(message: String) {
        logger.finer(message)
    }

    fun fine(event: GenericGuildEvent, message: String) {
        fine("${event.guild.name} | $message")
    }

    fun fine(message: String) {
        logger.fine(message)
    }

    fun info(event: GenericGuildEvent, message: String) {
        info("${event.guild.name}: $message")
    }

    fun info(message: String) {
        logger.info(message)
    }

    fun warning(event: GenericGuildEvent, message: String) {
        warning("${event.guild.name} | $message")
    }

    fun warning(message: String) {
        logger.warning(message)
    }

    fun severe(event: GenericGuildEvent, message: String) {
        severe("${event.guild.name} | $message")
    }

    fun severe(message: String) {
        logger.severe(message)
    }
}
