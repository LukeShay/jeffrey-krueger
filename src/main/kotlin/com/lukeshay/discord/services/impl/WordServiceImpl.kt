package com.lukeshay.discord.services.impl

import com.lukeshay.discord.entities.Word
import com.lukeshay.discord.entities.WordType
import com.lukeshay.discord.logging.createLogger
import com.lukeshay.discord.repositories.WordRepository
import com.lukeshay.discord.services.SnowflakeService
import com.lukeshay.discord.services.WordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class WordServiceImpl @Autowired constructor(
    private val wordRepository: WordRepository,
    private val snowflakeService: SnowflakeService
) :
    WordService {
    override fun new(guildId: Long, singular: String, plural: String, type: WordType): Word? {
        val newWord = Word(
            id = snowflakeService.getSnowflakeId(),
            guildId = guildId,
            type = type.toString(),
            singular = singular,
            plural = plural
        )

        val word = wordRepository.findBySlug(newWord.slug)

        if (word != null) return null

        return wordRepository.save(newWord)
    }

    override fun randomPluralNoun(guildId: Long): String {
        return try {
            wordRepository.findAllByGuildIdAndType(guildId, WordType.NOUN.toString())
                .random().plural
        } catch (e: Exception) {
            logger.warning("error getting noun: $e")
            "summoners"
        }
    }

    override fun randomPluralVerb(guildId: Long): String {
        return try {
            wordRepository.findAllByGuildIdAndType(guildId, WordType.VERB.toString())
                .random().plural
        } catch (e: Exception) {
            logger.warning("error getting verb: $e")
            "summoners"
        }
    }

    override fun randomSingularNoun(guildId: Long): String {
        return try {
            wordRepository.findAllByGuildIdAndType(guildId, WordType.NOUN.toString())
                .random().singular
        } catch (e: Exception) {
            logger.warning("error getting noun: $e")
            "summoner"
        }
    }

    override fun randomSingularVerb(guildId: Long): String {
        return try {
            wordRepository.findAllByGuildIdAndType(guildId, WordType.VERB.toString())
                .random().singular
        } catch (e: Exception) {
            logger.warning("error getting verb: $e")
            "summoner"
        }
    }

    companion object {
        private val logger = createLogger("WordServiceImpl")
    }
}
