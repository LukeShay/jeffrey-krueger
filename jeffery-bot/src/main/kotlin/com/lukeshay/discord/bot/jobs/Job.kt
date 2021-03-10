package com.lukeshay.discord.bot.jobs

import com.lukeshay.discord.logging.createLogger
import java.time.Clock
import java.time.LocalDateTime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.JDA

abstract class Job(private val name: String) {
    lateinit var jda: JDA

    abstract suspend fun execute()

    fun run() {
        jda.awaitReady()

        GlobalScope.launch {
            logger.info("starting job - $name")

            while (true) {
                delay(1000 * 60 * 60)

                if (LocalDateTime.now(Clock.systemUTC()).hour == 23) {
                    logger.info("executing job - $name")
                    execute()
                }
            }
        }
    }

    companion object {
        val logger = createLogger(Job::class.java)
    }
}