package com.lukeshay.discord.repositories

import com.lukeshay.discord.entities.GuildConfig
import org.springframework.data.jpa.repository.JpaRepository

interface GuildConfigRepository : JpaRepository<GuildConfig, Long>
