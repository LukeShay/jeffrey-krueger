package com.lukeshay.discord.logging

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

fun <T> createLogger(clazz: Class<T>): Logger {
    return LogManager.getLogger(clazz)
}