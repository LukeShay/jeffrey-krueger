package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.entities.Words
import com.lukeshay.discord.enums.Emoji
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.logging.createLogger

open class WordCommand(
    private val cmd: String,
    desc: String,
    leader: Boolean,
    environment: Environment,
    private val wordType: WordType,
) : Command(cmd, desc, leader, environment, listOf(), true) {
    override suspend fun run(event: CommandEvent) {
        val splitMessage = wordRegex.toRegex().matchEntire(event.contentRaw)!!.groups

        val singular = splitMessage["singular"]
        val plural = splitMessage["plural"]

        if (singular == null) {
            event.reply("That is an invalid number of arguments. Example !adjective small")
                .queue()
        } else {
            val message =
                if (Words.insertWord(
                        event.guildId,
                        singular.value,
                        plural?.value,
                        wordType
                    ) != null
                ) {
                    "Your $cmd has been saved!"
                } else {
                    "There was an error saving your $cmd ${Emoji.CRY}"
                }

            event.reply(message).queue()
        }
    }

    companion object {
        private const val wordRegex = """.+"(?<singular>.+)"( "(?<plural>.+)")?"""
        private val logger = createLogger(WordCommand::class.java)
    }
}
