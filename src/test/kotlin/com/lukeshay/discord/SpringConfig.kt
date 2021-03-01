package com.lukeshay.discord

import com.lukeshay.discord.enums.Environment
import com.lukeshay.discord.jobs.Job
import com.mchange.v2.c3p0.ComboPooledDataSource
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@ComponentScan("com.lukeshay.discord")
@EnableJpaRepositories("com.lukeshay.discord.repositories")
class SpringConfig {

    @Bean
    @Profile("test")
    fun jdaBuilder(listeners: List<ListenerAdapter>, jobs: List<Job>): JDABuilder {
        val builder =
            JDABuilder.createDefault(System.getenv("DISCORD_TOKEN").orEmpty())

        builder.setAutoReconnect(true)
        builder.setStatus(OnlineStatus.ONLINE)

        return builder
    }

    @Bean
    @Profile("test")
    fun environment(): Environment {
        return Environment.determineEnv()
    }

    @Bean
    @Profile("test")
    fun dataSource(): DataSource {
        val dataSource = ComboPooledDataSource()
        dataSource.jdbcUrl = "jdbc:h2:mem:testdb"
        dataSource.driverClass = "org.h2.Driver"

        return dataSource
    }

    @Bean
    @Profile("test")
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(true)
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan("com.lukeshay.discord.entities")
        factory.dataSource = dataSource
        return factory
    }

    @Bean
    @Profile("test")
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager? {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}
