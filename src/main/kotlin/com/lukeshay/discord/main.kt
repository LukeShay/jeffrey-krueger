package com.lukeshay.discord

import com.lukeshay.discord.config.Config
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    val ctx = AnnotationConfigApplicationContext(Config::class.java)
    ctx.getBean(Bot::class.java).start()
}
