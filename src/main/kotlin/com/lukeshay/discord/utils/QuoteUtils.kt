package com.lukeshay.discord.utils

import com.lukeshay.discord.entities.Quote
import com.lukeshay.discord.entities.Quotes
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

fun selectOneQuoteByGuildId(id: Long): Quote? = Quotes.select { Quotes.guildId eq id }
    .map { it }.randomOrNull()?.let { resultRowToQuote(it) }

private fun resultRowToQuote(it: ResultRow): Quote =
    Quote(
        id = it[Quotes.id],
        quote = it[Quotes.quote],
        author = it[Quotes.author],
        date = it[Quotes.date],
        guildId = it[Quotes.guildId],
    )
