package com.lukeshay.discord.services

import com.lukeshay.discord.entities.Quote

interface QuoteService {
    fun findOne(guildId: Long = 0): Quote?
}
