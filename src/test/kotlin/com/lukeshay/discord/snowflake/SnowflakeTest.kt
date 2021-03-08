package com.lukeshay.discord.snowflake

import com.lukeshay.discord.utils.Snowflake
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SnowflakeTest {
    @Test
    fun `generate returns unique IDs`() = runBlocking {
        val snowflake = Snowflake(1)

        val ids = mutableListOf<Long>()

        for (i in 1..100000) {
            ids.add(snowflake.generate())
        }

        Assertions.assertEquals(ids.toSet().size, ids.size)
    }
}
