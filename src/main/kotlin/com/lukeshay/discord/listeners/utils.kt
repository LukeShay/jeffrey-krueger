package com.lukeshay.discord.listeners

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.listeners.exceptions.UnauthorizedCommandRuntimeException
import net.dv8tion.jda.api.events.guild.GenericGuildEvent

fun shouldRun(environment: Environment, event: GenericGuildEvent) {
    if (!environment.isAllowed(event.guild)) throw UnauthorizedCommandRuntimeException("not allowed in this environment")
}
