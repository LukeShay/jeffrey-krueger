package com.lukeshay.discord.jobs

import com.lukeshay.discord.logging.DBLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.lukeshay.discord.services.GuildConfigService
import com.lukeshay.discord.services.WordService

@Component
class DailyGreeting @Autowired constructor(
    private val wordService: WordService,
    private val guildConfigService: GuildConfigService,
) : Job("daily greeting") {
    companion object {
        private val logger = DBLogger("DailyGreeting")
    }

    override suspend fun execute() {
        guildConfigService.findAll().filter { it.dailyGreeting }.map { it.defaultChannelId }
            .forEach {
                jda.getTextChannelById(it)
                    ?.sendMessage("Hey ${wordService.randomPluralNoun()}, How is it going?")
                    ?.queue()
            }
    }
}
