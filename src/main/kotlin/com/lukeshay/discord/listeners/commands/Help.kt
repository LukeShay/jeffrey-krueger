package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.enums.Environment
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

class Help(private val commands: List<Command>, environment: Environment) :
    Command(
        "help",
        "Displays this message.",
        true,
        environment,
        listOf("options")
    ) {

    override fun run(event: CommandEvent) {
        val msgEmbedBuilder = EmbedBuilder()
        msgEmbedBuilder.setTitle("Available Commands")
        msgEmbedBuilder.setColor(Color.GREEN)
        msgEmbedBuilder.descriptionBuilder.append("Hey! My name is Jeffrey Krueger. Here is a list of what I am capable of.")
        msgEmbedBuilder.setFooter("All of my commands are case insensitive. That means !HeLp will work.")

        commands.sortedBy { it.command }.forEach {
            msgEmbedBuilder.addField(it.command, it.description, false)
        }

        msgEmbedBuilder.addField("!help", description, false)

        if (environment != Environment.PRODUCTION) {
            msgEmbedBuilder.addField("environment", environment.toString(), true)
        }

        event.sendMessage(msgEmbedBuilder.build()).queue()
    }
}
