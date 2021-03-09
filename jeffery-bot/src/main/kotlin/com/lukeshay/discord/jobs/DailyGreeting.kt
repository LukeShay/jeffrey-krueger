package com.lukeshay.discord.jobs

import com.lukeshay.discord.entities.GuildConfigs
import com.lukeshay.discord.entities.Quotes
import com.lukeshay.discord.enums.Emoji
import org.apache.logging.log4j.LogManager

class DailyGreeting : Job("daily greeting") {
    override suspend fun execute() {
        GuildConfigs.all().filter { it.dailyGreeting }.forEach { gc ->
            jda.getTextChannelById(gc.defaultChannelId)?.sendMessage(
                Quotes.selectOneQuoteByGuildId(gc.id)?.format()
                    ?: "A quote could not be found ${Emoji.CRY}"
            )?.queue()
                ?: logger.error("could not send message to guild, guild id: ${gc.id}, channel id ${gc.defaultChannelId}")
        }
    }

    companion object {
        private val logger = LogManager.getLogger(DailyGreeting::class.java)
    }
}