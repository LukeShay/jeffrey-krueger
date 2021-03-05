package com.lukeshay.discord.config

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.Properties
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
class DataAccess {

    private val databaseURL = System.getProperty("database.url") ?: throw Exception("database.url not found")

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

    @Bean
    fun jdbcTemplate(): JdbcTemplate {
        return JdbcTemplate(dataSource())
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(true)
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan("com.lukeshay.discord.entities")
        factory.dataSource = dataSource()

        val props = Properties()

        props.setProperty("hibernate.hbm2ddl.auto", "update")
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")

        return factory
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager? {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }

    companion object {
        const val DB_URL_REGEX =
            """postgres://(?<username>[^:]+):(?<password>[^@]+)@(?<domain>.*)"""
        private val logger = LogManager.getLogger(DataAccess::class.java)
    }
}
