package com.lukeshay.discord.entities

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class QuoteTest {
    @Test
    fun `format has date`() {
        Assertions.assertEquals(
            "\"This is a test\" - Turkey Sandwich, January 12th, 2021",
            Quote(
                author = "Turkey Sandwich",
                quote = "This is a test",
                date = "January 12th, 2021"
            ).format()
        )
    }

    @Test
    fun `format does not have date`() {
        Assertions.assertEquals(
            "\"This is a test\" - Turkey Sandwich",
            Quote(
                author = "Turkey Sandwich",
                quote = "This is a test"
            ).format()
        )
    }
}
