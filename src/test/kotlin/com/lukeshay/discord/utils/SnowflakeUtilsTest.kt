package com.lukeshay.discord.utils

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SnowflakeUtilsTest {
    @Test
    fun `generate returns unique IDs`() = runBlocking {
        val ids = mutableListOf<Long>()

        for (i in 1..100000) {
            ids.add(SnowflakeUtils.getSnowflakeId())
        }

        Assertions.assertEquals(ids.toSet().size, ids.size)
    }
}
