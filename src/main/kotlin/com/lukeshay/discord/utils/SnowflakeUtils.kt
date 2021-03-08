package com.lukeshay.discord.utils

private var snowflake: Snowflake? = null

suspend fun getSnowflakeIdV2(): Long {
    if (snowflake == null) {
        snowflake = Snowflake(
            Integer.parseInt(
                System.getProperty(
                    "node.id",
                    "1"
                )
            )
        )
    }

    return snowflake!!.generate()
}
