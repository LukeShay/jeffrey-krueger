package com.lukeshay.discord.jobs

import com.lukeshay.discord.utils.Rand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Test @Autowired constructor(private val rand: Rand) : Job("test", 1000 * 60 * 60) {
    override fun execute() {
        jda.getGuildById(1)?.members
        jda.getTextChannelById(705810781710712903)!!
            .sendMessage("Hey ${rand.randomPluralVerb()}, How is it going?").queue()
    }
}
