package com.lukeshay.discord.listeners

import com.lukeshay.discord.entities.GuildConfigs
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.Issue
import com.lukeshay.discord.setupExposed
import com.lukeshay.discord.utils.GuildConfigUtils
import com.lukeshay.discord.utils.SnowflakeUtils
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.requests.restaction.MessageAction
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.properties.Delegates

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class OnGuildJoinTest {
    private var guildId by Delegates.notNull<Long>()
    private var ownerId by Delegates.notNull<Long>()

    @RelaxedMockK
    private lateinit var guildMock: Guild

    @RelaxedMockK
    private lateinit var guildEventMock: GuildJoinEvent

    @RelaxedMockK
    private lateinit var textChannelMock: TextChannel

    @RelaxedMockK
    private lateinit var messageActionMock: MessageAction

    @BeforeAll
    fun beforeAll() {
        setupExposed()
    }

    @BeforeEach
    fun setUp() {
        runBlocking {
            guildId = SnowflakeUtils.getSnowflakeId()
            ownerId = SnowflakeUtils.getSnowflakeId()

            every { textChannelMock.sendMessage(any<String>()) } returns messageActionMock

            every { guildMock.idLong } returns guildId
            every { guildMock.id } returns guildId.toString()
            every { guildMock.ownerIdLong } returns ownerId
            every { guildMock.defaultChannel } returns textChannelMock

            every { guildEventMock.guild } returns guildMock
        }
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(GuildConfigUtils)

        transaction {
            GuildConfigs.deleteWhere { GuildConfigs.id eq guildId }
        }
    }

    @Test
    fun `onGuildJoin inserts new guild`() = transaction {
        OnGuildJoin(Environment.PRODUCTION).onGuildJoin(guildEventMock)

        verify {
            textChannelMock
                .sendMessage("Thank you for adding me to your server! Send the message '!help' for information on my commands.")
            messageActionMock.queue()
        }

        Assertions.assertNotNull(GuildConfigUtils.selectById(guildMock))
    }

    @Test
    fun `onGuildJoin doesn't update guild`() = transaction {
        mockkObject(GuildConfigUtils)
        every { GuildConfigUtils.insertOrUpdate(guildMock) } returns null

        OnGuildJoin(Environment.PRODUCTION).onGuildJoin(guildEventMock)

        verify {
            GuildConfigUtils.insertOrUpdate(guildMock)
            textChannelMock
                .sendMessage("There was an error adding your guild to our database. Please open a ticket here: ${Issue.GUILD_TICKET}")
            messageActionMock.queue()
        }

        Assertions.assertNull(GuildConfigUtils.selectById(guildMock))
    }
}
