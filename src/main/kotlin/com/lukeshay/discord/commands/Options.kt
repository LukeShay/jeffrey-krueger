package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class Options(private val commands: List<Command>) :
    Command("options", "Displays this message.", true, FeatureStatus.RELEASE) {

    override fun run(event: GuildMessageReceivedEvent) {
        sendHelpMessage(event, commands)
    }
}
