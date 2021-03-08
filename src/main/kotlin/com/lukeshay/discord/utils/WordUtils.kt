package com.lukeshay.discord.utils

import com.lukeshay.discord.entities.Word
import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.entities.Words
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

fun selectOneWordByGuildIdAndType(id: Long, type: WordType): Word? =
    Words.select { (Words.guildId eq id) and (Words.type eq type.toString()) }
        .map { it }.randomOrNull()?.let { resultRowToWord(it) }

fun selectOneSingularNounByGuildId(id: Long): String =
    selectOneWordByGuildIdAndType(id, WordType.NOUN)?.singular ?: "summoner"

fun selectOneSingularAdjectiveByGuildId(id: Long): String =
    selectOneWordByGuildIdAndType(id, WordType.ADJECTIVE)?.singular ?: "nugget"

fun selectOneSingularVerbByGuildId(id: Long): String =
    selectOneWordByGuildIdAndType(id, WordType.VERB)?.singular ?: "run"

suspend fun insertWord(
    guildId: Long,
    singular: String,
    plural: String,
    type: WordType,
): Word? {
    val id = getSnowflakeIdV2()

    Words.insert {
        it[Words.id] = id
        it[Words.guildId] = guildId
        it[Words.singular] = singular
        it[Words.plural] = plural
        it[Words.type] = type.toString()
    }

    return Words.select { Words.id eq id }.firstOrNull()?.let { resultRowToWord(it) }
}

private fun resultRowToWord(it: ResultRow): Word =
    Word(
        id = it[Words.id],
        singular = it[Words.singular],
        plural = it[Words.plural],
        type = WordType.fromString(it[Words.type]) ?: WordType.NOUN,
        guildId = it[Words.guildId],
    )
