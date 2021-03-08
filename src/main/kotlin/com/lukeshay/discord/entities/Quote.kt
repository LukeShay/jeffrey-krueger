package com.lukeshay.discord.entities

import org.jetbrains.exposed.sql.Table

object Quotes : Table("quotes") {
    val id = long("id")
    val author = varchar("author", 50).nullable()
    val quote = varchar("quote", 255)
    val date = varchar("date", 17).nullable()
    val guildId = long("guild_id") references GuildConfigs.id
    override val primaryKey = PrimaryKey(GuildConfigs.id, name = "pk_quotes_id")
}

data class Quote(
    val id: Long,
    val author: String?,
    val quote: String,
    val date: String?,
    val guildId: Long
) {
    fun format() = "\"${quote}\" - ${author}${if (date != null && date != "") ", $date" else ""}"
}
