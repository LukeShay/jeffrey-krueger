package com.lukeshay.discord.entities

import com.lukeshay.discord.utils.Snowflake
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

data class Word(
    val id: Long,
    val type: WordType,
    val singular: String,
    val plural: String?,
    val guildId: Long
)

object Words : org.jetbrains.exposed.sql.Table("words") {
    val id = long("id")
    val type = varchar("type", 9)
    val singular = varchar("singular", 30)
    val plural = varchar("plural", 32).nullable()
    val guildId = long("guild_id") references GuildConfigs.id
    override val primaryKey = PrimaryKey(GuildConfigs.id, name = "pk_words_id")

    fun selectOneWordByGuildIdAndType(id: Long, type: WordType): Word? =
        select { (guildId eq id) and (Words.type eq type.toString()) }
            .map { it }.randomOrNull()?.let(::resultRowToWord)

    fun selectOneSingularNounByGuildId(id: Long): String =
        selectOneWordByGuildIdAndType(id, WordType.NOUN)?.singular ?: "summoner"

    fun selectOneSingularAdjectiveByGuildId(id: Long): String =
        selectOneWordByGuildIdAndType(id, WordType.ADJECTIVE)?.singular ?: "nugget"

    fun selectOneSingularVerbByGuildId(id: Long): String =
        selectOneWordByGuildIdAndType(id, WordType.VERB)?.singular ?: "run"

    suspend fun insertWord(
        guildId: Long,
        singular: String,
        plural: String?,
        type: WordType,
    ): Word? {
        val id = Snowflake.getSnowflakeId()

        insert {
            it[Words.id] = id
            it[Words.guildId] = guildId
            it[Words.singular] = singular
            it[Words.plural] = plural
            it[Words.type] = type.toString()
        }

        return select { Words.id eq id }.firstOrNull()?.let(::resultRowToWord)
    }

    private fun resultRowToWord(it: ResultRow): Word =
        Word(
            id = it[id],
            singular = it[singular],
            plural = it[plural],
            type = WordType.fromString(it[type]) ?: WordType.NOUN,
            guildId = it[guildId],
        )
}

enum class WordType {
    VERB, ADJECTIVE, NOUN;

    companion object {
        fun fromString(str: String): WordType? {
            return when (str) {
                VERB.toString() -> VERB
                ADJECTIVE.toString() -> ADJECTIVE
                NOUN.toString() -> NOUN
                else -> null
            }
        }
    }
}