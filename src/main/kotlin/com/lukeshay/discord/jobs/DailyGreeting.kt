package com.lukeshay.discord.jobs

import com.lukeshay.discord.enums.Emoji
import com.lukeshay.discord.utils.GuildConfigUtils
import com.lukeshay.discord.utils.QuoteUtils
import org.apache.logging.log4j.LogManager

class DailyGreeting : Job("daily greeting") {
    override suspend fun execute() {
        GuildConfigUtils.selectAll().forEach { gc ->
            jda.getTextChannelById(gc.defaultChannelId)?.sendMessage(
                QuoteUtils.selectOneQuoteByGuildId(gc.id)?.format()
                    ?: "A quote could not be found ${Emoji.CRY}"
            )?.queue()
                ?: logger.error("could not send message to guild, guild id: ${gc.id}, channel id ${gc.defaultChannelId}")
        }
    }

    companion object {
        private val logger = LogManager.getLogger(DailyGreeting::class.java)
    }
}
