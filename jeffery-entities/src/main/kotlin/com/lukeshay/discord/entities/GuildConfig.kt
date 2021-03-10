package com.lukeshay.discord.entities

class GuildConfig(
    val id: Long,
    val defaultChannelId: Long,
    val dailyGreeting: Boolean,
    val dailyQuote: Boolean,
    val commands: Boolean,
    val ownerId: Long,
    val adminIds: List<Long> = listOf(),
    val adminRoleIds: List<Long> = listOf(),
)