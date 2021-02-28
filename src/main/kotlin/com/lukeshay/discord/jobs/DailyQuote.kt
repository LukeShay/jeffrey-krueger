package com.lukeshay.discord.jobs

import com.lukeshay.discord.logging.DBLogger
import com.lukeshay.discord.services.GuildConfigService
import com.lukeshay.discord.services.QuoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DailyQuote @Autowired constructor(
    private val guildConfigService: GuildConfigService,
    private val quoteService: QuoteService
) : Job("daily quote", 1000 * 30) {
    companion object {
        private val logger = DBLogger("DailyQuote")
    }

    override fun execute() {
        val quote = quoteService.findOne()

        if (quote == null) {
            logger.severe("there was an error getting a quote")
            return
        }

        guildConfigService.findAll().filter { it.dailyQuote }.map { it.defaultChannelId }.forEach {
            jda.getTextChannelById(it)
                ?.sendMessage("Quote of the day: ${quote.format()}")?.queue()
        }
    }
}
