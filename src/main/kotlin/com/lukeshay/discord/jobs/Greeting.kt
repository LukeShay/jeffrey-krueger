package com.lukeshay.discord.jobs

import com.lukeshay.discord.services.WordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Greeting @Autowired constructor(
    private val wordService: WordService
) : Job("greeting", 1000 * 60 * 60) {
    override fun execute() {
        jda.getGuildById(1)?.members
        jda.getTextChannelById(705810781710712903)!!
            .sendMessage("Hey ${wordService.randomPluralVerb()}, How is it going?").queue()
    }
}
