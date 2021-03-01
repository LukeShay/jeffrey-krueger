package com.lukeshay.discord.services

import com.lukeshay.discord.entities.Word
import com.lukeshay.discord.entities.WordType

interface WordService {
    fun new(singular: String, plural: String, type: WordType): Word?
    fun randomPluralNoun(): String
    fun randomPluralVerb(): String
    fun randomSingularNoun(): String
    fun randomSingularVerb(): String
    fun save(singular: String, plural: String, type: WordType): Word
}
