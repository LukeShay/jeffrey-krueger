package com.lukeshay.discord.web.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class User(
    @SerialName("id") val id: String = "",
    @SerialName("username") val username: String = "",
    @SerialName("discriminator") val discriminator: String = "",
    @SerialName("avatar") val avatar: String? = "",
    @SerialName("email") val email: String? = "",
)