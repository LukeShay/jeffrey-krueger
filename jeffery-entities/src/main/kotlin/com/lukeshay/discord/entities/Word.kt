package com.lukeshay.discord.entities

data class Word(
    val id: Long,
    val type: WordType,
    val singular: String,
    val plural: String?,
    val guildId: Long
)

