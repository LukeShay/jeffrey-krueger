package com.lukeshay.discord.utils

import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.entities.Words
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

fun selectOneWordByGuildIdAndType(id: Long, type: WordType) =
    Words.select { (Words.guildId eq id) and (Words.type eq type.toString()) }
        .map { it }.randomOrNull()

fun selectOneNounByGuildId(id: Long) =
    selectOneWordByGuildIdAndType(id, WordType.NOUN) ?: "summoner"

fun selectOneAdjectiveByGuildId(id: Long) =
    selectOneWordByGuildIdAndType(id, WordType.ADJECTIVE) ?: "nugget"

fun selectOneVerbByGuildId(id: Long) =
    selectOneWordByGuildIdAndType(id, WordType.VERB) ?: "run"

fun insertWord(
    id: Long,
    guildId: Long,
    singular: String,
    plural: String,
    type: WordType
): ResultRow? {
    Words.insert {
        it[Words.id] = id
        it[Words.guildId] = guildId
        it[Words.singular] = singular
        it[Words.plural] = plural
        it[Words.type] = type.toString()
    }

    return Words.select { Words.id eq id }.firstOrNull()
}
