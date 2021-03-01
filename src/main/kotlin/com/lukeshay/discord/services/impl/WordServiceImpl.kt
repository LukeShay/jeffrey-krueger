package com.lukeshay.discord.services.impl

import com.lukeshay.discord.domain.Word
import com.lukeshay.discord.domain.WordType
import com.lukeshay.discord.logging.DBLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.lukeshay.discord.repositories.WordRepository
import com.lukeshay.discord.services.WordService
import javax.transaction.Transactional

@Service
@Transactional
class WordServiceImpl @Autowired constructor(private val wordRepository: WordRepository) :
    WordService {
    override fun new(singular: String, plural: String, type: WordType): Word? {
        val newWord = Word(type = type.toString(), singular = singular, plural = plural)

        return wordRepository.findBySlug(newWord.slug)?.let {
            null
        } ?: wordRepository.save(newWord)
    }

    override fun randomPluralNoun(): String {
        return try {
            wordRepository.findAllByType(WordType.NOUN.toString()).random().plural
        } catch (e: Exception) {
            logger.warning("error getting noun: $e")
            "summoners"
        }
    }

    override fun randomPluralVerb(): String {
        return try {
            wordRepository.findAllByType(WordType.VERB.toString()).random().plural
        } catch (e: Exception) {
            logger.warning("error getting verb: $e")
            "summoners"
        }
    }

    override fun randomSingularNoun(): String {
        return try {
            wordRepository.findAllByType(WordType.NOUN.toString()).random().singular
        } catch (e: Exception) {
            logger.warning("error getting noun: $e")
            "summoner"
        }
    }

    override fun randomSingularVerb(): String {
        return try {
            wordRepository.findAllByType(WordType.VERB.toString()).random().singular
        } catch (e: Exception) {
            logger.warning("error getting verb: $e")
            "summoner"
        }
    }

    override fun save(singular: String, plural: String, type: WordType): Word {
        return wordRepository.save(
            Word(
                type = type.toString(),
                singular = singular,
                plural = plural
            )
        )
    }

    companion object {
        private val logger = DBLogger("WordServiceImpl")
    }
}
