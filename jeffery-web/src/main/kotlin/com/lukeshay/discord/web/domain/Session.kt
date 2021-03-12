package com.lukeshay.discord.web.domain

import com.lukeshay.discord.sdk.requests.Me
import com.lukeshay.discord.sdk.requests.OAuth2TokenResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class Session(
    @SerialName("access_token") val accessToken: String = "",
    @SerialName("token_type") val tokenType: String = "",
    @SerialName("expires_in") val expiresIn: Long = 0,
    @SerialName("refresh_token") val refreshToken: String = "",
    @SerialName("scope") val scope: String = "",
    val id: String = "",
    val username: String = "",
    val discriminator: String = "",
    val avatar: String? = "",
    val email: String? = "",
) {
    fun toUser(): User = User(id, username, discriminator, avatar, email)
    fun toOAuth2TokenRequest(): OAuth2TokenResponse =
        OAuth2TokenResponse(accessToken, tokenType, expiresIn, refreshToken, scope)

    companion object {
        fun fromOAuth2AndUser(session: OAuth2TokenResponse, user: Me): Session = Session(
            session.accessToken,
            session.tokenType,
            session.expiresIn,
            session.refreshToken,
            session.scope,
            user.id,
            user.username,
            user.discriminator,
            user.avatar,
        )
    }
}