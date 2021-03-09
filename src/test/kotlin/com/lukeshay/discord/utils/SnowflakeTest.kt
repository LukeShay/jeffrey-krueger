package com.lukeshay.discord.utils

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeExactly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
class SnowflakeTest : ShouldSpec({
    context("getSnowflakeId") {
        should("return unique IDs") {
            runBlockingTest {
                val ids = mutableListOf<Long>()

                for (i in 1..1000) {
                    ids.add(Snowflake.getSnowflakeId())
                }

                ids.size shouldBeExactly ids.toSet().size
            }
        }
    }
})
