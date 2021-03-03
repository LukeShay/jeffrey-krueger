package com.lukeshay.discord.services

import com.lukeshay.discord.domain.Snowflake

interface SnowflakeService {
    fun getSnowflake(): Snowflake
    fun getSnowflakeId(): Long
}
