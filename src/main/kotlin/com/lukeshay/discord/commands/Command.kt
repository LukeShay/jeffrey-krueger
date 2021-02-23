package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import com.lukeshay.discord.utils.leaderChar
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

abstract class Command(
    cmd: String,
    val description: String,
    leader: Boolean,
    val status: FeatureStatus
) {

    val command = "${if (leader) leaderChar else ""}${cmd.toLowerCase()}"

    abstract fun run(event: GuildMessageReceivedEvent)
}
