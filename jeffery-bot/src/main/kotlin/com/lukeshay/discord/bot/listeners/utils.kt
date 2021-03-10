package com.lukeshay.discord.bot.listeners

import com.lukeshay.discord.bot.enums.Environment
import com.lukeshay.discord.bot.listeners.exceptions.UnauthorizedCommandRuntimeException
import net.dv8tion.jda.api.events.guild.GenericGuildEvent

fun shouldRun(environment: Environment, event: GenericGuildEvent) {
    if (!environment.isAllowed(event.guild)) throw UnauthorizedCommandRuntimeException("not allowed in this environment")
}