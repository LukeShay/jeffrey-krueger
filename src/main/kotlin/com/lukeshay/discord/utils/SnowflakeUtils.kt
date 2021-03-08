package com.lukeshay.discord.utils

object SnowflakeUtils {
    private val snowflake: Snowflake = Snowflake(
        Integer.parseInt(
            System.getProperty(
                "node.id",
                "1"
            )
        )
    )

    suspend fun getSnowflakeId(): Long {
        return snowflake.generate()
    }
}
