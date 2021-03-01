package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.FeatureStatus
import com.lukeshay.discord.logging.DBLogger
import com.lukeshay.discord.services.GuildConfigService
import com.lukeshay.discord.services.WordService

open class WordCommand(
    private val cmd: String,
    desc: String,
    leader: Boolean,
    status: FeatureStatus,
    environment: Environment,
    private val wordType: WordType,
    private val guildConfigService: GuildConfigService,
    private val wordService: WordService
) : Command(cmd, desc, leader, status, environment) {
    override fun run(event: CommandEvent) {
        val splitMessage = WORD_REGEX.toRegex().matchEntire(event.contentRaw)!!.groups

        guildConfigService.findByGuildId(event.guildId)?.let {
            if (event.authorAsMember == null || !it.canEdit(event.authorAsMember)) {
                return
            }
        }

        val singular = splitMessage["singular"]
        val plural = splitMessage["plural"]

        if (singular == null) {
            event.reply("That is an invalid number of arguments. Example !adjective small")
                .queue()
        } else {
            try {
                wordService.new(singular.value, plural?.value ?: "", wordType)?.let {
                    event.reply("Your $cmd has been saved!").queue()
                } ?: event.reply("That $cmd already exists").queue()
            } catch (e: Exception) {
                e.printStackTrace()
                logger.severe("error saving $cmd: $e")
                event.reply("There was an error saving your $cmd!").queue()
            }
        }
    }

    companion object {
        private const val WORD_REGEX = """.+"(?<singular>.+)" "(?<plural>.+)"?"""
        private val logger = DBLogger("WordCommand")
    }
}
