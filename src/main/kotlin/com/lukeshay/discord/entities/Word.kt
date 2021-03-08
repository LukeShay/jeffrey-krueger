package com.lukeshay.discord.entities

object Words : org.jetbrains.exposed.sql.Table("words") {
    val id = long("id")
    val type = varchar("type", 9)
    val singular = varchar("singular", 30)
    val plural = varchar("plural", 32).nullable()
    val guildId = long("guild_id") references GuildConfigs.id
    override val primaryKey = PrimaryKey(GuildConfigs.id, name = "pk_words_id")
}

data class Word(
    val id: Long,
    val type: WordType,
    val singular: String,
    val plural: String?,
    val guildId: Long
)

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
