package com.lukeshay.discord.listeners

import com.lukeshay.discord.info
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class OnGuildMemberJoin : ListenerAdapter() {

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        info(event, "join - ${event.member.effectiveName}")
    }
}