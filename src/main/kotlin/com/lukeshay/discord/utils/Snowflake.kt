package com.lukeshay.discord.utils

import kotlinx.coroutines.sync.Mutex
import org.joda.time.DateTime

class Snowflake(
    private val nodeId: Int,
    private val nodeBits: Int = 10,
    private val stepBits: Int = 12,
    private val epochTime: Long = 1288834974657,
) {
    private val mutex = Mutex(true)
    private val nodeMax = -1 xor (-1 shl nodeBits)
    private val stepMask = -1 xor (-1 shl stepBits)
    private val timeShift = nodeBits + stepBits
    private val nodeShift = stepBits
    private var time = DateTime.now().minus(epochTime)

    private var step = 0

    init {
        if (nodeId < 0 || nodeId > nodeMax) {
            throw Exception("Node number must be between 0 and $nodeMax")
        }

        mutex.unlock()
    }

    suspend fun generate(): Long {
        mutex.lock(this)

        var now = DateTime.now().minus(epochTime)

        if (now.equals(time)) {
            step = (step + 1) and stepMask

            if (step == 0) {
                while (now.isBefore(time) || now.equals(time)) {
                    now = time.minus(now.millis)
                }
            }
        } else {
            step = 0
        }

        time = now

        mutex.unlock(this)

        return (now.millis shl timeShift or ((nodeId shl nodeShift).toLong()) or step.toLong()) % 1000000000000000000
    }
}
