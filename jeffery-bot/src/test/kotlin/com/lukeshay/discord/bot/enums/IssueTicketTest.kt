package com.lukeshay.discord.bot.enums

import io.kotest.core.spec.style.ShouldSpec
import org.junit.jupiter.api.Assertions

class IssueTicketTest : ShouldSpec({
    context("url") {
        should("return a Github URL") {
            val url = Issue.FEATURE_REQUEST.toString()

            Assertions.assertTrue(url.contains("github.com/LukeShay/jeffery-krueger"))
        }

        should("contain correct template for FEATURE_REQUEST") {
            val url = Issue.FEATURE_REQUEST.toString()

            Assertions.assertTrue(url.contains("template=feature_request.md"))
        }

        should("contain correct template for BUG_REPORT") {
            val url = Issue.BUG_REPORT.toString()

            Assertions.assertTrue(url.contains("template=bug_report.md"))
        }

        should("contain correct template for GUILD_TICKET") {
            val url = Issue.GUILD_TICKET.toString()

            Assertions.assertTrue(url.contains("template=guild_ticket.md"))
        }
    }
})