package com.lukeshay.discord.enums

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EmojiTest {
    @Test
    fun `toString returns the discord emoji`() {
        Assertions.assertEquals(":ping_pong:", Emoji.PING_PONG.toString())
    }
}
