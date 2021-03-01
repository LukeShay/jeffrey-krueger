package com.lukeshay.discord.jobs

import com.lukeshay.discord.logging.DBLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.lukeshay.discord.services.GuildConfigService
import com.lukeshay.discord.services.QuoteService

@Component
class DailyQuote @Autowired constructor(
    private val guildConfigService: GuildConfigService,
    private val quoteService: QuoteService
) : Job("daily quote") {
    companion object {
        private val logger = DBLogger("DailyQuote")
    }

    override fun execute() {
        quoteService.findOne()?.let {
            guildConfigService.findAll().filter { it.dailyQuote }.map { it.defaultChannelId }
                .forEach {
                    jda.getTextChannelById(it)
                        ?.sendMessage("Quote of the day: ${it.format()}")?.queue()
                }
        } ?: logger.severe("there was an error getting a quote")
    }
}
