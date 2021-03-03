package com.lukeshay.discord.services.impl

import com.lukeshay.discord.entities.GuildConfig
import com.lukeshay.discord.logging.createLogger
import com.lukeshay.discord.repositories.GuildConfigRepository
import com.lukeshay.discord.services.GuildConfigService
import net.dv8tion.jda.api.entities.Guild
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GuildConfigServiceImpl @Autowired constructor(private val guildConfigRepository: GuildConfigRepository) :
    GuildConfigService {
    override fun findAll(): List<GuildConfig> {
        return try {
            guildConfigRepository.findAll()
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("error finding guilds: $e")
            listOf()
        }
    }

    override fun findById(guildId: Long): GuildConfig? {
        return try {
            val guildConfigOptional = guildConfigRepository.findById(guildId)

            if (guildConfigOptional.isPresent) {
                guildConfigOptional.get()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("error finding guild: $e")
            null
        }
    }

    override fun new(guild: Guild): GuildConfig? {
        return findById(guild.idLong)?.let { null } ?: guildConfigRepository.save(
            GuildConfig(
                id = guild.idLong,
                defaultChannelId = guild.defaultChannel?.idLong ?: 0,
                ownerId = guild.ownerIdLong,
                adminIds = mutableSetOf(guild.ownerIdLong),
            )
        )
    }

    override fun save(guildConfig: GuildConfig): GuildConfig? {
        return try {
            guildConfigRepository.save(guildConfig)
        } catch (e: Exception) {
            logger.severe("error saving guild $e")
            null
        }
    }

    override fun saveOrUpdate(guild: Guild): GuildConfig? {
        val guildConfig = findById(guild.idLong) ?: return new(guild)

        guildConfig.ownerId = guild.ownerIdLong
        guildConfig.adminIds.add(guild.ownerIdLong)
        guildConfig.defaultChannelId = guild.defaultChannel?.idLong ?: 0

        guildConfig.dailyGreeting = true
        guildConfig.dailyQuote = true

        return save(guildConfig)
    }

    companion object {
        private val logger = createLogger("GuildConfigServiceImpl")
    }
}
