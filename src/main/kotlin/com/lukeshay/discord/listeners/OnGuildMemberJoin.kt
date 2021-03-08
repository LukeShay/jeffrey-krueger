package com.lukeshay.discord.listeners

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.utils.WordUtils
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class OnGuildMemberJoin(private val environment: Environment) :
    ListenerAdapter() {
    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        shouldRun(environment, event)
        event.guild.defaultChannel?.sendMessage(
            "Everyone, say hello to the newest ${
            WordUtils.selectOneSingularNounByGuildId(
                event.guild.idLong
            )
            } - ${event.member.asMention}"
        )
    }
}
