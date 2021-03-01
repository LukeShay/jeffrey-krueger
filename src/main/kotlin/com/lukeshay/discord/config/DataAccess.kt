package com.lukeshay.discord.config

import com.lukeshay.discord.logging.DBLogger
import com.mchange.v2.c3p0.ComboPooledDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories("com.lukeshay.discord.repositories")
@EnableTransactionManagement
class DataAccess {
    @Bean
    fun dataSource(): DataSource {
        val dbURL = try {
            System.getenv("DATABASE_URL") ?: "postgres://username:password@localhost:5432/postgres"
        } catch (e: Exception) {
            e.printStackTrace()
            logger.severe("error getting database url from env: $e")
            "postgres://username:password@localhost:5432/postgres"
        }

        val groups = DB_URL_REGEX.toRegex().matchEntire(dbURL)!!.groups

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
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(true)
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan("com.lukeshay.discord.entities")
        factory.dataSource = dataSource

        val props = Properties()

        props.setProperty("hibernate.hbm2ddl.auto", "update")

        factory.setJpaProperties(props)
        return factory
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager? {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }

    companion object {
        const val DB_URL_REGEX = """postgres://(?<username>[^:]+):(?<password>[^@]+)@(?<domain>.*)"""
        private val logger = DBLogger("DataAccess")
    }
}
