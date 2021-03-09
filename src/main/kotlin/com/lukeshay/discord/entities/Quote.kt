package com.lukeshay.discord.entities

import com.lukeshay.discord.utils.Snowflake
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

data class Quote(
    val id: Long,
    val author: String?,
    val quote: String,
    val date: String?,
    val guildId: Long
) {
    fun format() = "\"${quote}\" - ${author}${if (date != null && date != "") ", $date" else ""}"
}

object Quotes : Table("quotes") {
    val id = long("id")
    val quote = varchar("quote", 255)
    val author = varchar("author", 50).nullable()
    val date = varchar("date", 17).nullable()
    val guildId = long("guild_id") references GuildConfigs.id
    override val primaryKey = PrimaryKey(GuildConfigs.id, name = "pk_quotes_id")

    fun selectOneQuoteByGuildId(id: Long): Quote? = Quotes.select { guildId eq id }
        .map { it }.randomOrNull()?.let(::resultRowToQuote)

    fun findById(id: Long): Quote? =
        select { Quotes.id eq id }.firstOrNull()?.let(::resultRowToQuote)

    suspend fun insertQuote(
        guildId: Long,
        quote: String,
        author: String?,
        date: String?,
    ): Quote? {
        val id: Long = Snowflake.getSnowflakeId()

        Quotes.insert {
            it[Quotes.id] = id
            it[Quotes.guildId] = guildId
            it[Quotes.quote] = quote
            it[Quotes.author] = author
            it[Quotes.date] = date
        }

        return findById(id)
    }

    private fun resultRowToQuote(it: ResultRow): Quote =
        Quote(
            id = it[id],
            quote = it[quote],
            author = it[author],
            date = it[date],
            guildId = it[guildId],
        )
}
