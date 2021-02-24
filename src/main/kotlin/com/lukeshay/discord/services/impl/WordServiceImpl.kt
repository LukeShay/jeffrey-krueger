package com.lukeshay.discord.services.impl

import com.lukeshay.discord.repositories.WordRepository
import com.lukeshay.discord.services.WordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WordServiceImpl @Autowired constructor(private val wordRepository: WordRepository) :
    WordService {
    override fun randomPluralVerb(): String {
        val plural = wordRepository.findAll().random().plural
        return if (plural != "") plural else "summoners"
    }

    override fun randomSingularVerb(): String {
        val singular = wordRepository.findAll().random().singular
        return if (singular != "") singular else "summoner"
    }
}
