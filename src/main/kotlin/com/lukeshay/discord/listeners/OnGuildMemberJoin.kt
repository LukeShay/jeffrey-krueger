package com.lukeshay.discord.listeners

import com.lukeshay.discord.logging.DBLogger
import com.lukeshay.discord.utils.Rand
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OnGuildMemberJoin @Autowired constructor(private val rand: Rand) : ListenerAdapter() {
    companion object {
        private val logger = DBLogger("OnGuildMemberJoin")
    }

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        logger.info(event, "join - ${event.member.effectiveName}")

        event.guild.defaultChannel?.sendMessage("Everyone, say hello to the newest ${rand.randomVerb()} - ${event.member.asMention}")
    }
}
