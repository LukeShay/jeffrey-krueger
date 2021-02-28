package com.lukeshay.discord.services

import com.lukeshay.discord.domain.Quote

interface QuoteService {
    fun findAuthors(): Set<String>
    fun findOneByAuthor(author: String): Quote?
    fun findOne(): Quote?
}
