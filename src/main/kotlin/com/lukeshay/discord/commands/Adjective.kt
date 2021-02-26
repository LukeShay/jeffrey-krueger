package com.lukeshay.discord.commands

import com.lukeshay.discord.FeatureStatus
import com.lukeshay.discord.domain.WordType
import com.lukeshay.discord.services.WordService
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component

@Component
class Adjective @Autowired constructor(private val wordService: WordService) :
    Command("adjective", "adds the adjective to the database", true, FeatureStatus.PRE_ALPHA) {
    override fun run(event: GuildMessageReceivedEvent) {
        val splitMessage = event.message.contentRaw.split(" ")

        if (splitMessage.size != 2) {
            event.message.reply("That is an invalid number of arguments. Example !adjective small")
                .queue()
        } else {
            val singular = splitMessage[1]

            try {
                val word = wordService.new(singular, "", WordType.ADJECTIVE)

                if (word != null) {
                    event.message.reply("Your adjective has been saved!").queue()
                } else {
                    event.message.reply("That adjective already exists").queue()
                }
            } catch (e: DataAccessException) {
                event.message.reply("There was an error saving your adjective!").queue()
            }
        }
    }
}