package com.lukeshay.discord.logging

import org.apache.logging.log4j.LogManager

// fun createLogger(clazz: String): Logger {
//    val logger = Logger.getLogger(clazz)
//    logger.level = Level.FINEST
//    logger.useParentHandlers = false
//    logger.handlers.forEach { handler -> logger.removeHandler(handler) }
//
//    val consoleHandler = ConsoleHandler()
//
//    consoleHandler.formatter = LogFormatter()
//
//    logger.addHandler(consoleHandler)
//
//    return logger
// }

fun <T> createLogger(clazz: Class<T>): org.apache.logging.log4j.Logger {
    return LogManager.getLogger(clazz)
}
