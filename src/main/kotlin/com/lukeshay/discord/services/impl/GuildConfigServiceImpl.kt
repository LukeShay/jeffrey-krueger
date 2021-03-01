package com.lukeshay.discord.services.impl

import com.lukeshay.discord.domain.GuildConfig
import com.lukeshay.discord.logging.DBLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.lukeshay.discord.repositories.GuildConfigRepository
import com.lukeshay.discord.services.GuildConfigService

@Service
class GuildConfigServiceImpl @Autowired constructor(private val guildConfigRepository: GuildConfigRepository) :
    GuildConfigService {
    override fun findAll(): List<GuildConfig> {
        return try {
            guildConfigRepository.findAll()
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("error finding guild: $e")
            listOf()
        }
    }

    override fun findByGuildId(guildId: String): GuildConfig? {
        return try {
            guildConfigRepository.findByGuildId(guildId)
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("error finding guild: $e")
            null
        }
    }

    companion object {
        private val logger = DBLogger("GuildConfigServiceImpl")
    }
}
