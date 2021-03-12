package com.lukeshay.discord.utils

import com.mchange.v2.c3p0.ComboPooledDataSource
import javax.sql.DataSource

object DataAccessUtils {
    private val logger = LoggingUtils.createLogger(DataAccessUtils::class.java)

    private const val DB_URL_REGEX =
        """postgres://(?<username>[^:]+):(?<password>[^@]+)@(?<domain>.*)"""

    fun dataSource(
        databaseURL: String =
            System.getProperty("database.url") ?: throw Exception("database.url not found")
    ): DataSource {
        logger.info("database url: $databaseURL")

        val groups = DB_URL_REGEX.toRegex().matchEntire(databaseURL)!!.groups

        val dataSource = ComboPooledDataSource()
        dataSource.jdbcUrl = "jdbc:postgresql://${groups["domain"]!!.value}"
        dataSource.user = groups["username"]!!.value
        dataSource.password = groups["password"]!!.value
        dataSource.driverClass = "org.postgresql.Driver"

        return dataSource
    }
}