package com.lukeshay.discord.repositories

import com.lukeshay.discord.domain.GuildConfig
import org.springframework.data.jpa.repository.JpaRepository

interface GuildConfigRepository : JpaRepository<GuildConfig, Long> {
    fun findByGuildId(guildId: String): GuildConfig
}