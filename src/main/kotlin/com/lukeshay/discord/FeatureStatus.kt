package com.lukeshay.discord

import net.dv8tion.jda.api.entities.Category

enum class FeatureStatus {
    PRE_ALPHA, ALPHA, BETA, RELEASE;

    /**
     * Returns the list of the IDs of categories that the feature can be used
     * in. If the list is null, it can be used in all categories.
     */
    fun allowedCategories(): List<String>? {
        return when (this) {
            PRE_ALPHA -> listOf("813515790187364383")
            ALPHA -> listOf("813515790187364383", "520712780676857867")
            BETA -> listOf("813515790187364383", "520712780676857867")
            RELEASE -> null
        }
    }

    /**
     * Checks if the feature is allowed in the given category.
     */
    fun isAllowed(category: Category?): Boolean {
        return category != null && (
            allowedCategories() == null || allowedCategories()?.contains(
                category.id
            ) == true
            )
    }
}
