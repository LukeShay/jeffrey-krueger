package com.lukeshay.discord.commands

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

abstract class Command(val command: String) {
    open fun handle(event: GuildMessageReceivedEvent): Boolean {
        val message = event.message.contentRaw

        return if (message.startsWith("!$command", ignoreCase = true)) {
            run(event)
            true
        } else {
            false
        }
    }

    abstract fun run(event: GuildMessageReceivedEvent)

}