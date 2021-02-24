package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color

fun sendHelpMessage(event: GuildMessageReceivedEvent, commands: List<Command>) {
    val msgEmbedBuilder = EmbedBuilder()
    msgEmbedBuilder.setTitle("Available Commands")
    msgEmbedBuilder.setColor(Color.GREEN)
    msgEmbedBuilder.descriptionBuilder.append("Hey! My name is Jeffrey Krueger. You may know my degenerate brother, Tyler Krueger. Here is a list of what I am capable of.")
    msgEmbedBuilder.setFooter("All of my commands are case insensitive. That means !HeLp will work.")

    commands.filter { command -> command.status == FeatureStatus.RELEASE }
        .sortedBy { command -> command.command }.forEach { command ->
            msgEmbedBuilder.addField(command.command, command.description, false)
        }

    msgEmbedBuilder.addField("!options", "Displays this message.", false)
    msgEmbedBuilder.addField("!help", "Displays this message.", false)

    event.channel.sendMessage(msgEmbedBuilder.build()).queue()
}
