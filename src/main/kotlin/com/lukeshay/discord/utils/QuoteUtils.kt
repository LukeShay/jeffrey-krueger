package com.lukeshay.discord.utils

import com.lukeshay.discord.entities.Quotes
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

fun selectOneQuoteByGuildId(id: Long): ResultRow? = Quotes.select { Quotes.guildId eq id }
    .map { it }.randomOrNull()

fun formatQuote(quote: ResultRow): String =
    "\"${quote[Quotes.quote]}\" - ${quote[Quotes.author]}${if (quote[Quotes.date] != null && quote[Quotes.date] != "") ", ${quote[Quotes.date]}" else ""}"
