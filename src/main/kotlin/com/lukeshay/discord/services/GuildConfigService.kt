package com.lukeshay.discord.services

import com.lukeshay.discord.domain.GuildConfig

interface GuildConfigService {
    fun findAll(): List<GuildConfig>
    fun findByGuildId(guildId: String): GuildConfig?
}
