package com.lukeshay.discord.enums

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IssueTicketTest {
    @Test
    fun `toString returns a Github URL`() {
        val url = Issue.FEATURE_REQUEST.toString()

        Assertions.assertTrue(url.contains("github.com/LukeShay/jeffery-krueger"))
    }

    @Test
    fun `FEATURE_REQUEST has correct template`() {
        val url = Issue.FEATURE_REQUEST.toString()

        Assertions.assertTrue(url.contains("template=feature_request.md"))
    }

    @Test
    fun `BUG_REPORT has correct template`() {
        val url = Issue.BUG_REPORT.toString()

        Assertions.assertTrue(url.contains("template=bug_report.md"))
    }

    @Test
    fun `GUILD_TICKET has correct template`() {
        val url = Issue.GUILD_TICKET.toString()

        Assertions.assertTrue(url.contains("template=guild_ticket.md"))
    }
}
