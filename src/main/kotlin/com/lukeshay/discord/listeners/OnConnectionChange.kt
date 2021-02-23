package com.lukeshay.discord.listeners

import com.lukeshay.discord.logging.DBLogger
import net.dv8tion.jda.api.events.DisconnectEvent
import net.dv8tion.jda.api.events.ExceptionEvent
import net.dv8tion.jda.api.events.ReconnectedEvent
import net.dv8tion.jda.api.events.ShutdownEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class OnConnectionChange : ListenerAdapter() {
    companion object {
        private val logger = DBLogger("OnTestClass")
    }

    override fun onReconnected(event: ReconnectedEvent) {
        super.onReconnected(event)
        logger.warning("reconnected")
    }

    override fun onDisconnect(event: DisconnectEvent) {
        super.onDisconnect(event)
        logger.warning("disconnected ${event.closeCode}")
    }

    override fun onShutdown(event: ShutdownEvent) {
        super.onShutdown(event)
        logger.warning("shutdown ${event.closeCode}")
    }

    override fun onException(event: ExceptionEvent) {
        super.onException(event)
        logger.warning("exception ${event.cause}")
    }
}
