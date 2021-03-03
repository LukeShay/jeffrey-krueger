package com.lukeshay.discord.services

import com.lukeshay.discord.entities.GuildConfig
import net.dv8tion.jda.api.entities.Guild

interface GuildConfigService {
    fun findAll(): List<GuildConfig>
    fun findById(guildId: Long): GuildConfig?
    fun new(guild: Guild): GuildConfig?
    fun saveOrUpdate(guild: Guild): GuildConfig?
}
