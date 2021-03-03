package com.lukeshay.discord.config

import com.beust.klaxon.Klaxon
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.jobs.Job
import com.lukeshay.discord.logging.createLogger
import com.mchange.v2.c3p0.ComboPooledDataSource
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.util.Properties
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@ComponentScan("com.lukeshay.discord")
@EnableJpaRepositories("com.lukeshay.discord.repositories")
@EnableTransactionManagement
class Config {

    @Bean
    fun jdaBuilder(listeners: List<ListenerAdapter>, jobs: List<Job>): JDABuilder {
        val builder =
            JDABuilder.createDefault(System.getenv("DISCORD_TOKEN").orEmpty())

        builder.setAutoReconnect(true)
        builder.setStatus(OnlineStatus.ONLINE)

        return builder
    }

    @Bean
    fun environment(): Environment {
        return Environment.determineEnv()
    }

    @Bean
    fun httpClient(): HttpClient {
        return HttpClient.newBuilder().build()
    }

    @Bean
    fun klaxon(): Klaxon {
        return Klaxon()
    }

    @Bean
    fun snowflakeHttpRequest(environment: Environment): HttpRequest {
        return HttpRequest.newBuilder().uri(URI.create(environment.snowflakeUrl)).build()
    }

    @Bean
    fun dataSource(): DataSource {
        val dbURL = try {
            System.getenv("DATABASE_URL") ?: "postgres://postgres:password@localhost:5432/postgres"
        } catch (e: Exception) {
            e.printStackTrace()
            logger.severe("error getting database url from env: $e")
            "postgres://postgres:password@localhost:5432/postgres"
        }

        logger.info("using database url: $dbURL")

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
        private val logger = createLogger("Config")
    }
}
