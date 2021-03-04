package com.lukeshay.discord.services

import com.lukeshay.discord.entities.Quote

interface QuoteService {
    /**
     * Finds a random quote for the given guild.
     *
     * @param guildId the ID of the guild you are getting a quote for
     * @return Quote if one is found or null if not
     */
    fun findOne(guildId: Long = 0): Quote?
}
