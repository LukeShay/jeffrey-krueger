package com.lukeshay.discord.sdk.requests

import com.lukeshay.discord.sdk.DiscordRequestException
import com.lukeshay.discord.sdk.DiscordSdk
import com.lukeshay.discord.sdk.domain.DiscordError
import com.lukeshay.discord.sdk.extensions.addOAuth2Headers
import com.lukeshay.discord.sdk.extensions.decodeFromJsonElementIgnoreUnknown
import com.lukeshay.discord.sdk.extensions.json
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable data class OAuth2TokenResponse(
    @SerialName("access_token") val accessToken: String = "",
    @SerialName("token_type") val tokenType: String = "",
    @SerialName("expires_in") val expiresIn: Long = 0,
    @SerialName("refresh_token") val refreshToken: String = "",
    @SerialName("scope") val scope: String = "",
)

suspend fun DiscordSdk.getOAuth2Token(
    grantType: String,
    code: String,
    scope: String,
    redirectUri: String,
): OAuth2TokenResponse {
    val parameters = Parameters.build {
        append("client_id", clientId)
        append("client_secret", clientSecret)
        append("grant_type", grantType)
        append("code", code)
        append("scope", scope)
        append("redirect_uri", redirectUri)
    }

    try {
        val response: HttpResponse =
            httpClient.submitForm("$discordApiUrl/oauth2/token", parameters)
        return Json.decodeFromJsonElementIgnoreUnknown(response.json())
    } catch (e: ClientRequestException) {
        val discordError = Json.decodeFromJsonElementIgnoreUnknown<DiscordError>(e.response.json())
        throw DiscordRequestException("error making oauth2 token request", discordError)
    }
}

suspend fun DiscordSdk.revokeOAuth2Token(oAuth2TokenResponse: OAuth2TokenResponse) {
    val parameters = Parameters.build {
        append("token", oAuth2TokenResponse.accessToken)
    }

    try {
        httpClient.submitForm<HttpResponse>("$discordApiUrl/oauth2/token/revoke", parameters) {
            addOAuth2Headers(oAuth2TokenResponse)
        }
    } catch (e: ClientRequestException) {
        val discordError = Json.decodeFromJsonElementIgnoreUnknown<DiscordError>(e.response.json())
        throw DiscordRequestException("error making oauth2 token revoke request", discordError)
    }
}