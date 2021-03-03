package com.lukeshay.discord.listeners

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.services.WordService
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OnGuildMemberJoin @Autowired constructor(
    private val wordService: WordService,
    private val environment: Environment
) :
    ListenerAdapter() {
    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        shouldRun(environment, event)
        event.guild.defaultChannel?.sendMessage("Everyone, say hello to the newest ${wordService.randomSingularNoun()} - ${event.member.asMention}")
    }
}
