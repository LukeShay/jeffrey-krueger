package com.lukeshay.discord.jobs

import com.lukeshay.discord.logging.DBLogger
import net.dv8tion.jda.api.JDA

abstract class Job(private val name: String, private val delay: Long) : Runnable {
    lateinit var jda: JDA;

    companion object {
        val logger = DBLogger("Job")
    }

    abstract fun execute()

    override fun run() {
        logger.info("starting job - $name")
        jda.awaitReady()
        execute()
        while (true) {
            Thread.sleep(delay)

            logger.info("executing job - $name")
            execute()
        }
    }
}