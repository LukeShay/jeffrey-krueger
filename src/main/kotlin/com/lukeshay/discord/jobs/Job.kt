package com.lukeshay.discord.jobs

import com.lukeshay.discord.logging.DBLogger
import net.dv8tion.jda.api.JDA
import java.time.Clock
import java.time.LocalDateTime

abstract class Job(private val name: String) : Runnable {
    lateinit var jda: JDA

    abstract fun execute()

    override fun run() {
        logger.info("starting job - $name")

        jda.awaitReady()

        while (true) {
            Thread.sleep(1000 * 60 * 60)

            if (LocalDateTime.now(Clock.systemUTC()).hour == 23) {
                logger.info("executing job - $name")
                execute()
            }
        }
    }

    companion object {
        val logger = DBLogger("Job")
    }
}
