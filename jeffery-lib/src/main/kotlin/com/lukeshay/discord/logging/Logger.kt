package com.lukeshay.discord.logging

import com.lukeshay.discord.bot.enums.Environment
import io.sentry.Sentry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

fun <T> createLogger(clazz: Class<T>): Logger {
    return LogManager.getLogger(clazz)
}

fun createLogger(clazz: String): Logger {
    return LogManager.getLogger(clazz)
}

fun setupLogger(environment: Environment = Environment.determineEnvironment()) {
    Sentry.init { options ->
        options.dsn =
            System.getProperty("sentry.dsn") ?: throw Exception("property sentry.dsn is not set")
        options.environment = environment.toString().toLowerCase()
        options.release =
            System.getProperty("app.version") ?: throw Exception("property app.version is not set")
    }
}