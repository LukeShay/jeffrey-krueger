package com.lukeshay.discord.sdk.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class DiscordError(
    val error: String? = "",
    @SerialName("error_description") val errorDescription: String? = "",
    val message: String? = "",
    val code: String? = "",
)