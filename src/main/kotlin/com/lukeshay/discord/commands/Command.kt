package com.lukeshay.discord.commands

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

abstract class Command(val command: String) {
    abstract fun run(event: GuildMessageReceivedEvent)
}