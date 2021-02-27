package com.lukeshay.discord.enums

import net.dv8tion.jda.api.entities.Category

enum class Environment {
    LOCAL, DEVELOPMENT, STAGING, PRODUCTION;

    companion object {
        fun determineEnv(): Environment {
            val env = System.getenv("ENVIRONMENT") ?: ""

            return when (env.toLowerCase()) {
                "production" -> PRODUCTION
                "staging" -> STAGING
                "development" -> DEVELOPMENT
                else -> LOCAL
            }
        }
    }

    /**
     * Returns the list of the IDs of categories that environment can be used
     * in. If the list is null, it can be used in all categories.
     */
    fun allowedCategories(): List<String>? {
        return when (this) {
            LOCAL -> listOf("813515790187364383")
            DEVELOPMENT -> listOf("813515790187364383", "520712780676857867")
            STAGING -> listOf("813515790187364383", "520712780676857867")
            PRODUCTION -> null
        }
    }

    /**
     * Checks if the environment is allowed in the given category.
     */
    fun isAllowed(category: Category?): Boolean {
        return category != null && (
            allowedCategories() == null || allowedCategories()?.contains(
                category.id
            ) == true
            )
    }
}
