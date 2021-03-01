package com.lukeshay.discord.services

import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.events.guild.GenericGuildEvent

interface MemberService {
    fun getMemberRoles(event: GenericGuildEvent, memberId: String): List<Role>
}
