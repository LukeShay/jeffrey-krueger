package com.lukeshay.discord.jobs

import com.lukeshay.discord.logging.createLogger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.JDA
import java.time.Clock
import java.time.LocalDateTime

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
