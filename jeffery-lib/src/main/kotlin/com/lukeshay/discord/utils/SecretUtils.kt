package com.lukeshay.discord.utils

import com.lukeshay.discord.bot.enums.Environment
import io.akeyless.client.api.V2Api
import io.akeyless.client.model.Configure
import io.akeyless.client.model.GetSecretValue

object SecretUtils {
    fun loadSecrets(
        properties: List<String>,
        environment: Environment = Environment.determineEnvironment(),
        aKeylessAccessId: String = System.getProperty("akeyless.access.id")
            ?: throw Exception("akeyless.access.id not found"),
        akeylessAccessKey: String = System.getProperty("akeyless.access.key")
            ?: throw Exception("akeyless.access.key not found")
    ) {
        val secrets: MutableMap<String, String> = mutableMapOf()

        properties.forEach { secrets[it] = it.replace(".", "-") }

        val apiClient = io.akeyless.client.Configuration.getDefaultApiClient()

        apiClient.basePath = "https://api.akeyless.io"

        val v2Api = V2Api(apiClient)

        val aKeylessToken = v2Api.configure(
            Configure().accessId(aKeylessAccessId).accessKey(akeylessAccessKey),
        ).token ?: throw Exception("error getting v2 api token")

        val secretsPath = "jeffery-krueger/${environment.toString().toLowerCase()}"

        val body = GetSecretValue().token(aKeylessToken)

        secrets.forEach { body.addNamesItem("$secretsPath/${it.value}") }

        val result = v2Api.getSecretValue(body)

        secrets.forEach {
            System.setProperty(
                it.key,
                result["$secretsPath/${it.value}"]
                    ?: throw Exception("${it.value} secret not found"),
            )
        }
    }
}