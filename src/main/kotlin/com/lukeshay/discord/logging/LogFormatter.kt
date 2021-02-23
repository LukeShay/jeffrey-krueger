package com.lukeshay.discord.logging

import java.util.logging.Formatter
import java.util.logging.LogRecord

class LogFormatter : Formatter() {
    override fun format(record: LogRecord): String {
        return "${record.instant} [${record.level}] ${record.sourceClassName}::${record.sourceMethodName} | ${record.message}\n"
    }
}
