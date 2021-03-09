package com.lukeshay.discord.listeners.commands

import com.lukeshay.discord.domain.CommandEvent
import com.lukeshay.discord.entities.GuildConfigs
import com.lukeshay.discord.entities.Word
import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.entities.Words
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.utils.Snowflake
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.fail
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import net.dv8tion.jda.api.requests.restaction.MessageAction
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

@ExperimentalCoroutinesApi
class WordCommandTest : ShouldSpec({
    val wordCommand: WordCommand = Noun(Environment.determineEnvironment())

    var guildId = 0L
    var word = Word(
        id = 1,
        type = WordType.NOUN,
        singular = "summoner",
        plural = "summoners",
        guildId = guildId
    )

    val eventMock = mockk<CommandEvent>(relaxed = true)
    val messageAction = mockk<MessageAction>(relaxed = true)

    beforeTest {
        guildId = Snowflake.getSnowflakeId()

        transaction {
            GuildConfigs.insert {
                it[id] = guildId
                it[dailyGreeting] = true
                it[dailyQuote] = true
                it[commands] = true
                it[ownerId] = 0
                it[defaultChannelId] = 0
            }
        }

        word = Word(
            id = 1,
            type = WordType.NOUN,
            singular = "summoner",
            plural = "summoners",
            guildId = guildId
        )

        every { eventMock.guildId } returns guildId
        every { eventMock.contentRaw } returns "!noun \"${word.singular}\" \"${word.plural}\""
        every { eventMock.reply(any()) } returns messageAction
    }

    afterTest {
        transaction {
            Words.deleteWhere { Words.guildId eq guildId }
            GuildConfigs.deleteWhere { GuildConfigs.id eq guildId }
        }
    }

    context("run") {
        should("save the word") {
            transaction {
                runBlockingTest {
                    wordCommand.run(eventMock)

                    val theWord = Words.selectOneWordByGuildIdAndType(guildId, WordType.NOUN)
                        ?: fail("word not found")

                    assertSoftly {
                        theWord.type shouldBe WordType.NOUN
                        theWord.singular shouldBe word.singular
                        theWord.plural shouldBe word.plural
                        theWord.guildId shouldBe word.guildId
                    }

                    verify {
                        eventMock.reply("Your noun has been saved!")
                        messageAction.queue()
                    }
                }
            }
        }

        should("respond with invalid number of arguments") {
            every { eventMock.contentRaw } returns "!noun asdf"

            wordCommand.run(eventMock)

            verify {
                eventMock.reply("That is an invalid number of arguments. Example !adjective \"small\"")
                messageAction.queue()
            }
        }
    }
})
