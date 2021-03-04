package com.lukeshay.discord.services.impl

import com.lukeshay.discord.entities.Quote
import com.lukeshay.discord.logging.createLogger
import com.lukeshay.discord.repositories.QuoteRepository
import com.lukeshay.discord.services.QuoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuoteServiceImpl @Autowired constructor(private val quoteRepository: QuoteRepository) :
    QuoteService {
    override fun findOne(guildId: Long): Quote? {
        return try {
            quoteRepository.findAllByGuildId(guildId).randomOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("error finding quite: $e")
            null
        }
    }

    companion object {
        private val logger = createLogger("QuoteServiceImpl")
    }
}
