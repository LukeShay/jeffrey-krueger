package com.lukeshay.discord.utils

class ListUtils {
    companion object {
        fun <T> toString(
            list: List<T>,
            separator: String = ", ",
            prefix: String = "",
            newLines: Boolean = false
        ): String {
            val sb = StringBuilder()

            sb.append("[")

            var first = true

            list.forEach { i ->
                if (first) {
                    first = false
                } else {
                    sb.append(separator)
                }

                if (newLines) {
                    sb.appendLine()
                }

                sb.append(prefix)
                sb.append("\"$i\"")
            }

            if (newLines) {
                sb.appendLine()
            }

            sb.append("]")

            return sb.toString()
        }
    }
}
