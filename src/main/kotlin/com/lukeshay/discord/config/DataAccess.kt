package com.lukeshay.discord.config

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataAccess {

    private val databaseURL =
        System.getProperty("database.url") ?: throw Exception("database.url not found")

    @Bean
    fun dataSource(): DataSource {
        logger.info("database url: $databaseURL")
        val groups = DB_URL_REGEX.toRegex().matchEntire(databaseURL)!!.groups

        val dataSource = ComboPooledDataSource()
        dataSource.jdbcUrl = "jdbc:postgresql://${groups["domain"]!!.value}"
        dataSource.user = groups["username"]!!.value
        dataSource.password = groups["password"]!!.value
        dataSource.driverClass = "org.postgresql.Driver"

        return dataSource
    }

    companion object {
        const val DB_URL_REGEX =
            """postgres://(?<username>[^:]+):(?<password>[^@]+)@(?<domain>.*)"""
        private val logger = LogManager.getLogger(DataAccess::class.java)
    }
}
