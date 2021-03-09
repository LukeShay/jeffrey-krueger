package com.lukeshay.discord.listeners

import com.lukeshay.discord.entities.GuildConfigs
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.enums.Issue
import com.lukeshay.discord.utils.Snowflake
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import kotlin.properties.Delegates
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.requests.restaction.MessageAction
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

class OnGuildJoinTest : ShouldSpec({
    var guildId by Delegates.notNull<Long>()
    var ownerId by Delegates.notNull<Long>()

    val guildMock = mockk<Guild>(relaxed = true)
    val guildEventMock = mockk<GuildJoinEvent>(relaxed = true)
    val textChannelMock = mockk<TextChannel>(relaxed = true)
    val messageActionMock = mockk<MessageAction>(relaxed = true)

    beforeTest {
        guildId = Snowflake.getSnowflakeId()
        ownerId = Snowflake.getSnowflakeId()

        every { textChannelMock.sendMessage(any<String>()) } returns messageActionMock

        every { guildMock.idLong } returns guildId
        every { guildMock.id } returns guildId.toString()
        every { guildMock.ownerIdLong } returns ownerId
        every { guildMock.defaultChannel } returns textChannelMock

        every { guildEventMock.guild } returns guildMock
    }

    afterTest {
        unmockkObject(GuildConfigs)

        transaction {
            GuildConfigs.deleteWhere { GuildConfigs.id eq guildId }
        }
    }

    context("onGuildJoin") {
        should("insert a new guild") {
            transaction {
                OnGuildJoin(Environment.PRODUCTION).onGuildJoin(guildEventMock)

                verify {
                    textChannelMock
                        .sendMessage("Thank you for adding me to your server! Send the message '!help' for information on my commands.")
                    messageActionMock.queue()
                }

                GuildConfigs.selectById(guildMock) shouldNotBe null
            }
        }

        should("not update guild") {
            mockkObject(GuildConfigs)
            every { GuildConfigs.insertOrUpdate(guildMock) } returns null

            transaction {
                OnGuildJoin(Environment.PRODUCTION).onGuildJoin(guildEventMock)

                verify {
                    GuildConfigs.insertOrUpdate(guildMock)
                    textChannelMock
                        .sendMessage("There was an error adding your guild to our database. Please open a ticket here: ${Issue.GUILD_TICKET}")
                    messageActionMock.queue()
                }

                GuildConfigs.selectById(guildMock) shouldBe null
            }
        }
    }
})