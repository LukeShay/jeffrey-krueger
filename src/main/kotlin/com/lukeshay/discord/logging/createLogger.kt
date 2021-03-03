package com.lukeshay.discord.logging

import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger

fun createLogger(clazz: String): Logger {
    val logger = Logger.getLogger(clazz)
    logger.level = Level.FINEST
    logger.useParentHandlers = false
    logger.handlers.forEach { handler -> logger.removeHandler(handler) }

    val consoleHandler = ConsoleHandler()

    consoleHandler.formatter = LogFormatter()

    logger.addHandler(consoleHandler)

    return logger
}
