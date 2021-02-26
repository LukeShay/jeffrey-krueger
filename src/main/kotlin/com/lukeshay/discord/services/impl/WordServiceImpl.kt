package com.lukeshay.discord.services.impl

import com.lukeshay.discord.domain.Word
import com.lukeshay.discord.domain.WordType
import com.lukeshay.discord.repositories.WordRepository
import com.lukeshay.discord.services.WordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class WordServiceImpl @Autowired constructor(private val wordRepository: WordRepository) :
    WordService {
    override fun randomPluralVerb(): String {
        val plural = wordRepository.findAllByType(WordType.VERB).random().plural
        return if (plural != "") plural else "summoners"
    }

    override fun randomSingularVerb(): String {
        val singular = wordRepository.findAllByType(WordType.VERB).random().singular
        return if (singular != "") singular else "summoner"
    }

    override fun randomPluralNoun(): String {
        val plural = wordRepository.findAllByType(WordType.NOUN).random().plural
        return if (plural != "") plural else "summoners"
    }

    override fun randomSingularNoun(): String {
        val singular = wordRepository.findAllByType(WordType.NOUN).random().singular
        return if (singular != "") singular else "summoner"
    }

    override fun save(singular: String, plural: String, type: WordType): Word {
        return wordRepository.save(Word(type = type, singular = singular, plural = plural))
    }

    override fun new(singular: String, plural: String, type: WordType): Word? {
        val newWord = Word(type = type, singular = singular, plural = plural)

        val word = wordRepository.getBySlug(newWord.slug)

        if (word != null) return null

        return wordRepository.save(newWord)
    }
}
