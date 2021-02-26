package com.lukeshay.discord.services

import com.lukeshay.discord.domain.Word
import com.lukeshay.discord.domain.WordType

interface WordService {
    fun randomPluralVerb(): String
    fun randomSingularVerb(): String
    fun randomPluralNoun(): String
    fun randomSingularNoun(): String
    fun save(singular: String, plural: String, type: WordType): Word
    fun new(singular: String, plural: String, type: WordType): Word?
}
