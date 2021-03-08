package com.lukeshay.discord

import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

fun mockkFunction(kfun: KFunction<*>) = mockkStatic(kfun.javaMethod!!.declaringClass.kotlin)

fun unmockkFunction(kfun: KFunction<*>) = unmockkStatic(kfun.javaMethod!!.declaringClass.kotlin)
