package com.lukeshay.discord.services

import com.lukeshay.discord.entities.GuildConfig
import net.dv8tion.jda.api.entities.Guild

interface GuildConfigService {
    /**
     * Finds all the GuildConfigs.
     */
    fun findAll(): List<GuildConfig>

    /**
     * Finds the guild config by the given ID.
     *
     * @param guildId the ID of the guild
     * @return GuildConfig if one is found or null if one is not found
     */
    fun findById(guildId: Long): GuildConfig?

    /**
     * Creates a new GuildConfig if one does not exist.
     *
     * @param guild the guild to create a config for
     * @return GuildConfig if one is created or null if one already exists
     */
    fun new(guild: Guild): GuildConfig?

    /**
     * Saves the given GuildConfig.
     *
     * @param guildConfig the GuildConfig to save
     * @return GuildConfig if one is saved or null if there is an error
     */
    fun save(guildConfig: GuildConfig): GuildConfig?

    /**
     * Creates a new GuildConfig if one does not exist for the given guild. If
     * the guild already has a configuration, it will be reset to the default
     * config.
     *
     * @param guild the guild to save or update
     * @return GuildConfig of the guild
     */
    fun saveOrUpdate(guild: Guild): GuildConfig?
}
