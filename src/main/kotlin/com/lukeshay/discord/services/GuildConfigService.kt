package com.lukeshay.discord.services

import com.lukeshay.discord.domain.GuildConfig

interface GuildConfigService {
    fun findByGuildId(guildId: String): GuildConfig?
    fun findAll(): List<GuildConfig>
}
