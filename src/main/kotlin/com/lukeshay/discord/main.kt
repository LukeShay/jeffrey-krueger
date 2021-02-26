package com.lukeshay.discord

import com.lukeshay.discord.config.Config
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    val ctx = AnnotationConfigApplicationContext(Config::class.java)
    val environment = ctx.getBean(Environment::class.java)
    val commit = ctx.getBean("commit", String::class.java)
    val jda = ctx.getBean(Bot::class.java).start()

    jda.awaitReady()

    println("Deployed $commit to $environment")

    if (environment != Environment.LOCAL) {
        jda.getTextChannelById(520712856744886283)?.sendMessage("Deployed $commit to $environment")
            ?.queue()
    }
}
