package com.lukeshay.discord.services.impl

import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.events.guild.GenericGuildEvent
import org.springframework.stereotype.Service
import com.lukeshay.discord.services.MemberService

@Service
class MemberServiceImpl : MemberService {
    override fun getMemberRoles(
        event: GenericGuildEvent,
        memberId: Long
    ): List<Role> {
        return event.guild.members.find { member -> member.idLong == memberId }?.roles!!.toList()
    }
}
