package com.lukeshay.discord

import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    ClassPathXmlApplicationContext("context.xml").getBean(Bot::class.java).start()
}
