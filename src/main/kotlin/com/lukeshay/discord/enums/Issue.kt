package com.lukeshay.discord.enums

enum class Issue(private val str: String) {
    BUG_REPORT("https://github.com/LukeShay/jeffery-krueger/issues/new?assignees=LukeShay&labels=bug%2C+awaiting+triage&template=bug_report.md&title=%5BBUG%5D+Your+title+here"),
    FEATURE_REQUEST("https://github.com/LukeShay/jeffery-krueger/issues/new?assignees=LukeShay&labels=feature%2C+awaiting+triage&template=feature_request.md&title=%5BFEATURE%5D+Your+title+here"),
    GUILD_TICKET("https://github.com/LukeShay/jeffery-krueger/issues/new?assignees=LukeShay&labels=guild%2C+awaiting+triage&template=guild_ticket.md&title=%5BGUILD%5D+Your+title+here");

    override fun toString(): String {
        return str
    }
}
