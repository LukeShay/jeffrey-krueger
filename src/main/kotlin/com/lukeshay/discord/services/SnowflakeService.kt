package com.lukeshay.discord.services

import com.lukeshay.discord.domain.Snowflake

interface SnowflakeService {
    /**
     * Gets a unique Snowflake from the Snowflake service.
     *
     * @return a Snowflake
     */
    fun getSnowflake(): Snowflake

    /**
     * Gets a unique Snowflake ID from the Snowflake service.
     *
     * @return a Snowflake ID
     */
    fun getSnowflakeId(): Long
}
