package com.lukeshay.discord.listeners

import com.lukeshay.discord.logging.DBLogger
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.lukeshay.discord.services.WordService

@Component
class OnGuildMemberJoin @Autowired constructor(private val wordService: WordService) :
    ListenerAdapter() {
    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        logger.info(event, "join - ${event.member.effectiveName}")

        event.guild.defaultChannel?.sendMessage("Everyone, say hello to the newest ${wordService.randomSingularNoun()} - ${event.member.asMention}")
    }

    companion object {
        private val logger = DBLogger("OnGuildMemberJoin")
    }
}
