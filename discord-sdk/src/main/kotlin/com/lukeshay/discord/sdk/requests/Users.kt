package com.lukeshay.discord.sdk.requests

import com.lukeshay.discord.sdk.DiscordRequestException
import com.lukeshay.discord.sdk.DiscordSdk
import com.lukeshay.discord.sdk.domain.DiscordError
import com.lukeshay.discord.sdk.extensions.addOAuth2Headers
import com.lukeshay.discord.sdk.extensions.decodeFromJsonElementIgnoreUnknown
import com.lukeshay.discord.sdk.extensions.json
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable data class Me(
    @SerialName("id") val id: String = "",
    @SerialName("username") val username: String = "",
    @SerialName("discriminator") val discriminator: String = "",
    @SerialName("avatar") val avatar: String? = "",
    @SerialName("email") val email: String? = "",
)

suspend fun DiscordSdk.getUsersMe(oAuth2TokenResponse: OAuth2TokenResponse): Me {
    try {
        val response: HttpResponse = httpClient.get("$discordApiUrl/users/@me") {
            addOAuth2Headers(oAuth2TokenResponse)
        }
        return Json.decodeFromJsonElementIgnoreUnknown(response.json())
    } catch (e: ClientRequestException) {
        val discordError = Json.decodeFromJsonElementIgnoreUnknown<DiscordError>(e.response.json())
        throw DiscordRequestException("error making oauth2 token request", discordError)
    }
}