package com.lukeshay.discord.services

import com.lukeshay.discord.GuildImpl
import com.lukeshay.discord.SpringTestBase
import com.lukeshay.discord.domain.Snowflake
import com.lukeshay.discord.entities.GuildConfig
import com.lukeshay.discord.repositories.GuildConfigRepository
import com.lukeshay.discord.services.impl.GuildConfigServiceImpl
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
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

    private val guildConfigRepositoryMock = Mockito.mock(GuildConfigRepository::class.java)
    private val guildConfigServiceWithMock: GuildConfigService =
        GuildConfigServiceImpl(guildConfigRepositoryMock)

    @BeforeEach
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

    @AfterEach
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
        val guild = guildConfigService.findById(guildConfig.id) ?: Assertions.fail()

        Assertions.assertEquals(guildConfig.id, guild?.id)
    }

    @Test
    fun `findByGuildId returns a null`() {
        val guild = guildConfigService.findById(guildConfig.id) ?: Assertions.fail()

        Assertions.assertEquals(guildConfig.id, guild?.id)
    }

    @Test
    fun `new creates a GuildConfig`() {
        guildConfigRepository.deleteById(snowflake.id)
        val guild = guildConfigService.new(GuildImpl("my guild", snowflake.id.toString()))
            ?: Assertions.fail()

        Assertions.assertEquals(snowflake.id, guild?.id)
    }

    @Test
    fun `new return null if a GuildConfig already exists`() {
        val guildImpl = GuildImpl("my guild", snowflake.id.toString())

        Assertions.assertNull(guildConfigService.new(guildImpl))
    }

    @Test
    fun `saveOrUpdate saves a new GuildConfig`() {
        val guild = guildConfigService.saveOrUpdate(GuildImpl("my guild", snowflake.id.toString()))
            ?: Assertions.fail()

        Assertions.assertEquals(snowflake.id, guild?.id)
    }

    @Test
    fun `saveOrUpdate updates an existing GuildConfig`() {
        val guildConfig = guildConfigService.save(
            GuildConfig(
                id = snowflake.id,
                defaultChannelId = channelIdSnowflake.id,
                dailyQuote = false,
                dailyGreeting = false
            )
        ) ?: Assertions.fail()

        val guild =
            guildConfigService.saveOrUpdate(GuildImpl("my guild", guildConfig.id.toString()))
                ?: Assertions.fail()

        Assertions.assertEquals(guildConfig.id, guild.id)
        Assertions.assertTrue(guild.dailyGreeting)
        Assertions.assertTrue(guild.dailyQuote)
        Assertions.assertTrue(guild.adminIds.isNotEmpty())
    }

    @Test
    fun `save returns null on exception`() {
        val guildConfig = GuildConfig()

        Mockito.`when`(guildConfigRepositoryMock.save(guildConfig))
            .thenThrow(RuntimeException())

        Assertions.assertNull(guildConfigServiceWithMock.save(guildConfig))
    }

    @Test
    fun `findById returns null on exception`() {
        Mockito.`when`(guildConfigRepositoryMock.findById(1))
            .thenThrow(RuntimeException())

        Assertions.assertNull(guildConfigServiceWithMock.findById(1))
    }

    @Test
    fun `findById returns null when no guild`() {
        Assertions.assertNull(guildConfigService.findById(snowflakeService.getSnowflakeId()))
    }

    @Test
    fun `findAll returns empty list on exception`() {
        Mockito.`when`(guildConfigRepositoryMock.findAll())
            .thenThrow(RuntimeException())

        Assertions.assertTrue(guildConfigServiceWithMock.findAll().isEmpty())
    }
}
