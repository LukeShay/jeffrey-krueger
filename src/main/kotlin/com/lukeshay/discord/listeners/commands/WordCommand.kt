package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.logging.createLogger
import com.lukeshay.discord.utils.getSnowflakeId
import com.lukeshay.discord.utils.insertWord
import com.lukeshay.discord.utils.isAdmin

open class WordCommand(
    private val cmd: String,
    desc: String,
    leader: Boolean,
    environment: Environment,
    private val wordType: WordType,
) : Command(cmd, desc, leader, environment, listOf(), true) {
    override fun run(event: CommandEvent) {
        val splitMessage = WORD_REGEX.toRegex().matchEntire(event.contentRaw)!!.groups

        if (event.authorAsMember == null || !isAdmin(event.guild, event.authorAsMember)) {
            return
        }

        val singular = splitMessage["singular"]
        val plural = splitMessage["plural"]

        if (singular == null) {
            event.reply("That is an invalid number of arguments. Example !adjective small")
                .queue()
        } else {
            try {
                val message =
                    insertWord(
                        getSnowflakeId(),
                        event.guildId,
                        singular.value,
                        plural?.value ?: "",
                        wordType
                    )
                        ?.let {
                            "Your $cmd has been saved!"
                        } ?: "That $cmd already exists"

                event.reply(message).queue()
            } catch (e: Exception) {
                e.printStackTrace()
                logger.error("error saving $cmd: $e")
                event.reply("There was an error saving your $cmd!").queue()
            }
        }
    }

    companion object {
        private const val WORD_REGEX = """.+"(?<singular>.+)" "(?<plural>.+)"?"""
        private val logger = createLogger(WordCommand::class.java)
    }
}
