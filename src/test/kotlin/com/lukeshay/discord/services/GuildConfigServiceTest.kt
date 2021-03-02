package com.lukeshay.discord.services

import com.lukeshay.discord.SpringTestBase
import com.lukeshay.discord.entities.GuildConfig
import com.lukeshay.discord.repositories.GuildConfigRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class GuildConfigServiceTest @Autowired constructor(
    private val guildConfigService: GuildConfigService,
    private val guildConfigRepository: GuildConfigRepository
) : SpringTestBase() {
    private lateinit var guildConfig: GuildConfig

    @BeforeAll
    fun setUp() {
        guildConfig = guildConfigRepository.save(
            GuildConfig(
                guildId = "1",
                defaultChannelId = "2",
                adminIds = mutableSetOf("3"),
                adminRoleIds = mutableSetOf("4"),
            )
        )
    }

    @Test
    fun `findAll returns all GuildConfigs`() {
        val guilds = guildConfigService.findAll()

        Assertions.assertEquals(1, guilds.size)
        Assertions.assertEquals(guildConfig.id, guilds.first().id)
    }

    @Test
    fun `findByGuildId returns a GuildConfig`() {
        val guild = guildConfigService.findByGuildId(guildConfig.guildId)

        Assertions.assertNotNull(guild)
        Assertions.assertEquals(guildConfig.id, guild?.id)
    }
}
