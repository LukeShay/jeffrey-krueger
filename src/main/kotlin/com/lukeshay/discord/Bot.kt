package com.lukeshay.discord

import com.lukeshay.discord.jobs.Job
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Bot @Autowired constructor(
    private val listeners: List<ListenerAdapter>,
    private val jobs: List<Job>,
    private val builder: JDABuilder,
) {
    fun start(): JDA {
        for (listener in listeners) {
            builder.addEventListeners(listener)
        }

        val jda = builder.build()

        if (System.getenv("SKIP_JOBS") != "true") {
            jobs.forEach { job ->
                run {
                    job.jda = jda
                    job.run()
                }
            }
        }

        return jda
    }
}
