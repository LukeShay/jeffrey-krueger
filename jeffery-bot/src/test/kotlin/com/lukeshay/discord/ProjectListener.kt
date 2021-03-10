package com.lukeshay.discord

import com.lukeshay.discord.entities.setupExposed
import io.kotest.core.listeners.ProjectListener
import io.kotest.core.spec.AutoScan

@AutoScan
object ProjectListener : ProjectListener {
    override suspend fun beforeProject() {
        setupExposed(dataSource())
    }
}