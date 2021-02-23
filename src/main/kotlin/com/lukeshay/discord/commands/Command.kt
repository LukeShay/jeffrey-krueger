package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

abstract class Command(val command: String, val status: FeatureStatus = FeatureStatus.RELEASE) {

    abstract fun run(event: GuildMessageReceivedEvent)
}