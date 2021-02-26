package com.lukeshay.discord.domain

enum class WordType {
    VERB, ADJECTIVE, NOUN;

    companion object {
        fun fromString(str: String): WordType? {
            return when (str) {
                VERB.toString() -> VERB
                ADJECTIVE.toString() -> ADJECTIVE
                NOUN.toString() -> NOUN
                else -> null
            }
        }
    }
}
