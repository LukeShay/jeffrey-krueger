package com.lukeshay.discord.services.impl

import com.lukeshay.discord.services.MemberService
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.events.guild.GenericGuildEvent
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl : MemberService {
    override fun getMemberRoles(
        event: GenericGuildEvent,
        memberId: String
    ): List<Role> {
        return event.guild.members.find { member -> member.id == memberId }?.roles!!.toList()
    }
}
