package com.lukeshay.discord.utils

import org.springframework.stereotype.Component

@Component
class Rand {
    private val verbs = System.getenv("VERBS").split(",").map { verb -> verb.trim() }
    private val pluralVerbs = System.getenv("PLURAL_VERBS").split(",").map { verb -> verb.trim() }

    fun randomVerb(): String {
        return verbs.random()
    }

    fun randomPluralVerb(): String {
        return pluralVerbs.random()
    }
}