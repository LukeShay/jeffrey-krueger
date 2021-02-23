package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class Help(private val commands: List<Command>) :
    Command("help", "Displays this message.", true, FeatureStatus.RELEASE) {

    override fun run(event: GuildMessageReceivedEvent) {
        sendHelpMessage(event, commands)
    }
}
