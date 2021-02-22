package com.lukeshay.discord

import net.dv8tion.jda.api.events.guild.GenericGuildEvent

fun info(event: GenericGuildEvent, message: String) {
    println("${event.guild.name} [info] $message")
}