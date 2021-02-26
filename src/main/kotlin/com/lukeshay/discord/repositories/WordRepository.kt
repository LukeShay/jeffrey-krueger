package com.lukeshay.discord.repositories

import com.lukeshay.discord.domain.Word
import com.lukeshay.discord.domain.WordType
import org.springframework.data.jpa.repository.JpaRepository

interface WordRepository : JpaRepository<Word, Long> {
    fun getBySlug(slug: String): Word?
    fun findAllByType(type: WordType): List<Word>
}
