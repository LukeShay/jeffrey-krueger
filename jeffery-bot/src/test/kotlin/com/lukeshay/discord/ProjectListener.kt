package com.lukeshay.discord

import io.kotest.core.listeners.ProjectListener
import io.kotest.core.spec.AutoScan

@AutoScan
object ProjectListener : ProjectListener {
    override suspend fun beforeProject() {
        setupExposed()
    }
}