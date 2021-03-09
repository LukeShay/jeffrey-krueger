package com.lukeshay.discord.listeners

import com.lukeshay.discord.entities.Words
import com.lukeshay.discord.enums.Environment
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class OnGuildMemberJoin(private val environment: Environment) :
    ListenerAdapter() {
    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        shouldRun(environment, event)
        event.guild.defaultChannel?.sendMessage(
            "Everyone, say hello to the newest ${Words.selectOneSingularNounByGuildId(event.guild.idLong)} - ${event.member.asMention}"
        )
    }
}