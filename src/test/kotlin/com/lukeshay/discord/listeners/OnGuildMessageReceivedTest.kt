package com.lukeshay.discord.listeners

import com.lukeshay.discord.GuildImpl
import com.lukeshay.discord.MessageImpl
import com.lukeshay.discord.TextChannelImpl
import com.lukeshay.discord.UserImpl
import com.lukeshay.discord.commands.Ping
import com.lukeshay.discord.listeners.exceptions.NoCommandRuntimeException
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.requests.restaction.MessageAction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class OnGuildMessageReceivedTest {

    private val guildMessageReceivedEvent = Mockito.mock(GuildMessageReceivedEvent::class.java)
    private val messageAction = Mockito.mock(MessageAction::class.java)

    private lateinit var onGuildMessageReceived: OnGuildMessageReceived

    private val commands = listOf(Ping())
    private lateinit var message: Message
    private lateinit var author: User
    private lateinit var guild: Guild
    private lateinit var channel: TextChannel


    @BeforeEach
    fun setUp() {
        onGuildMessageReceived = OnGuildMessageReceived(commands)

        author = UserImpl("Tyler Krueger", false)
        message = MessageImpl(author, "!ping this is a ping command")
        guild = GuildImpl("the guild")
        channel = TextChannelImpl(messageAction)

        Mockito.`when`(guildMessageReceivedEvent.author).thenReturn(author)
        Mockito.`when`(guildMessageReceivedEvent.message).thenReturn(message)
        Mockito.`when`(guildMessageReceivedEvent.guild).thenReturn(guild)
        Mockito.`when`(guildMessageReceivedEvent.channel).thenReturn(channel)
    }

    @Test
    fun onGuildMessageReceived_commandFound() {
        Assertions.assertDoesNotThrow {
            onGuildMessageReceived.onGuildMessageReceived(
                guildMessageReceivedEvent
            )
        }
    }

    @Test
    fun onGuildMessageReceived_commandNotFound() {
        message = MessageImpl(author, "!not-ping this is not a ping command")
        Mockito.`when`(guildMessageReceivedEvent.message).thenReturn(message)

        Assertions.assertThrows(NoCommandRuntimeException::class.java) {
            onGuildMessageReceived.onGuildMessageReceived(
                guildMessageReceivedEvent
            )
        }
    }
}