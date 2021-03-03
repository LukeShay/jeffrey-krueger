package com.lukeshay.discord.services

import com.lukeshay.discord.entities.Word
import com.lukeshay.discord.entities.WordType

interface WordService {
    fun new(guildId: Long, singular: String, plural: String, type: WordType): Word?
    fun randomPluralNoun(guildId: Long = 0): String
    fun randomPluralVerb(guildId: Long = 0): String
    fun randomSingularNoun(guildId: Long = 0): String
    fun randomSingularVerb(guildId: Long = 0): String
}
