package com.lukeshay.discord.jobs

import com.lukeshay.discord.enums.Emoji
import com.lukeshay.discord.utils.selectAllGuildConfigs
import com.lukeshay.discord.utils.selectOneQuoteByGuildId
import org.apache.logging.log4j.LogManager

class DailyGreeting : Job("daily greeting") {
    override suspend fun execute() {
        selectAllGuildConfigs().forEach { gc ->
            jda.getTextChannelById(gc.defaultChannelId)?.sendMessage(
                selectOneQuoteByGuildId(gc.id)?.format()
                    ?: "A quote could not be found ${Emoji.CRY}"
            )?.queue()
                ?: logger.error("could not send message to guild, guild id: ${gc.id}, channel id ${gc.defaultChannelId}")
        }
    }

    companion object {
        private val logger = LogManager.getLogger(DailyGreeting::class.java)
    }
}
