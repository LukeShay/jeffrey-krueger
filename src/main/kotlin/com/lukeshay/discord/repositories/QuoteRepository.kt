package com.lukeshay.discord.repositories

import com.lukeshay.discord.entities.Quote
import org.springframework.data.jpa.repository.JpaRepository

interface QuoteRepository : JpaRepository<Quote, Long> {
    fun findAllByGuildId(guildId: Long): List<Quote>
}
