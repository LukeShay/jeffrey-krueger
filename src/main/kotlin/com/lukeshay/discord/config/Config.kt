package com.lukeshay.discord.config

import com.beust.klaxon.Klaxon
import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.jobs.Job
import com.lukeshay.discord.logging.createLogger
import com.mchange.v2.c3p0.ComboPooledDataSource
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
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
@PropertySource("classpath:application-\${ENVIRONMENT:local}.properties")
class Config {

    @Value("\${discord.token}")
    private lateinit var discordToken: String

    @Value("\${snowflake.url}")
    private lateinit var snowflakeURL: String

    @Value("\${snowflake.client.secret}")
    private lateinit var snowflakeClientSecret: String

    @Value("\${database.url}")
    private lateinit var databaseURL: String

    @Bean
    fun jdaBuilder(listeners: List<ListenerAdapter>, jobs: List<Job>): JDABuilder {
        logger.info("discord token: $discordToken")

        val builder = JDABuilder.createDefault(discordToken)

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
        logger.info("snowflake url: $snowflakeURL")
        logger.info("snowflake client secret: $snowflakeClientSecret")

        val builder = HttpRequest.newBuilder().uri(URI.create(snowflakeURL))

        try {
            builder.header("X-Client-Secret", snowflakeClientSecret)
        } catch (e: NullPointerException) {
        }

        return builder.build()
    }

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
        private val logger = createLogger("Config")
    }
}
