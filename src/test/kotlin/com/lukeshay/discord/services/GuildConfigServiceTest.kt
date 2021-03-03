package com.lukeshay.discord.services

import com.lukeshay.discord.GuildImpl
import com.lukeshay.discord.SpringTestBase
import com.lukeshay.discord.domain.Snowflake
import com.lukeshay.discord.entities.GuildConfig
import com.lukeshay.discord.repositories.GuildConfigRepository
import net.dv8tion.jda.api.requests.restaction.MessageAction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired

internal class GuildConfigServiceTest @Autowired constructor(
    private val guildConfigService: GuildConfigService,
    private val guildConfigRepository: GuildConfigRepository,
    private val snowflakeService: SnowflakeService,
) : SpringTestBase() {
    private lateinit var snowflake: Snowflake
    private lateinit var channelIdSnowflake: Snowflake
    private lateinit var adminIdSnowflake: Snowflake
    private lateinit var adminRoleIdSnowflake: Snowflake
    private lateinit var guildConfig: GuildConfig
    private val messageAction = Mockito.mock(MessageAction::class.java)

    @BeforeAll
    fun setUp() {
        snowflake = snowflakeService.getSnowflake()
        channelIdSnowflake = snowflakeService.getSnowflake()
        adminIdSnowflake = snowflakeService.getSnowflake()
        adminRoleIdSnowflake = snowflakeService.getSnowflake()

        guildConfig = guildConfigRepository.save(
            GuildConfig(
                id = snowflake.id,
                defaultChannelId = channelIdSnowflake.id,
                adminIds = mutableSetOf(adminIdSnowflake.id),
                adminRoleIds = mutableSetOf(adminRoleIdSnowflake.id),
            )
        )
    }

    @AfterAll
    fun tearDown() {
        guildConfigRepository.deleteById(snowflake.id)
    }

    @Test
    fun `findAll returns all GuildConfigs`() {
        val guilds = guildConfigService.findAll()

        Assertions.assertTrue(guilds.isNotEmpty())
    }

    @Test
    fun `findByGuildId returns a GuildConfig`() {
        val guild = guildConfigService.findById(guildConfig.id)

        Assertions.assertNotNull(guild)
        Assertions.assertEquals(guildConfig.id, guild?.id)
    }

    @Test
    fun `findByGuildId returns a null`() {
        val guild = guildConfigService.findById(guildConfig.id)

        Assertions.assertNotNull(guild)
        Assertions.assertEquals(guildConfig.id, guild?.id)
    }

    @Test
    fun `new creates a GuildConfig`() {
        val guild = guildConfigService.new(GuildImpl("my guild", snowflake.id.toString()))

        Assertions.assertNotNull(guild)
        Assertions.assertEquals(snowflake.id, guild?.id)
    }
}
