package com.lukeshay.discord.services.impl

import com.lukeshay.discord.entities.Quote
import com.lukeshay.discord.logging.DBLogger
import com.lukeshay.discord.repositories.QuoteRepository
import com.lukeshay.discord.services.QuoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuoteServiceImpl @Autowired constructor(private val quoteRepository: QuoteRepository) :
    QuoteService {
    override fun findAuthors(): Set<String> {
        return try {
            quoteRepository.findAll().map { it.author }.toSet()
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("error finding quite: $e")
            setOf()
        }
    }

    override fun findOne(): Quote? {
        return try {
            quoteRepository.findAll().randomOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("error finding quite: $e")
            null
        }
    }

    override fun findOneByAuthor(author: String): Quote? {
        return try {
            quoteRepository.findAllByAuthor(author).random()
        } catch (e: Exception) {
            e.printStackTrace()
            logger.warning("error finding quite: $e")
            null
        }
    }

    companion object {
        private val logger = DBLogger("QuoteServiceImpl")
    }
}
