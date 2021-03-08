package com.lukeshay.discord.utils

import com.lukeshay.discord.entities.Quote
import com.lukeshay.discord.entities.Quotes
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

object QuoteUtils {

    fun selectOneQuoteByGuildId(id: Long): Quote? = Quotes.select { Quotes.guildId eq id }
        .map { it }.randomOrNull()?.let { resultRowToQuote(it) }

    fun findById(id: Long): Quote? =
        Quotes.select { Quotes.id eq id }.firstOrNull()?.let { resultRowToQuote(it) }

    suspend fun insertQuote(
        guildId: Long,
        quote: String,
        author: String?,
        date: String?,
    ): Quote? {
        val id: Long = SnowflakeUtils.getSnowflakeId()

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
            id = it[Quotes.id],
            quote = it[Quotes.quote],
            author = it[Quotes.author],
            date = it[Quotes.date],
            guildId = it[Quotes.guildId],
        )
}
