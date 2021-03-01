package com.lukeshay.discord.repositories

import com.lukeshay.discord.entities.Quote
import org.springframework.data.jpa.repository.JpaRepository

interface QuoteRepository : JpaRepository<Quote, Long> {
    fun findAllByAuthor(author: String): List<Quote>
}
