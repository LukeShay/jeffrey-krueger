package com.lukeshay.discord.services

import com.lukeshay.discord.SpringTestBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class SnowflakeServiceTest @Autowired constructor(
    private val snowflakeService: SnowflakeService
) : SpringTestBase() {
    @Test
    fun `snowflake returns a Snowflake`() {
        Assertions.assertNotNull(snowflakeService.getSnowflake())
    }

    @Test
    fun `snowflake returns a Snowflake ID`() {
        Assertions.assertTrue(0 < snowflakeService.getSnowflake().id)
    }
}
