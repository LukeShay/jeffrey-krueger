package com.lukeshay.discord.config

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories("com.lukeshay.discord.repositories")
@EnableTransactionManagement
class DataAccess {
    companion object {
        const val dbURLRegex = """postgres://(?<username>[^:]+):(?<password>[^@]+)@(?<domain>.*)"""
    }

    @Bean
    fun dataSource(): DataSource {
        val dbURL = System.getenv("DATABASE_URL")

        val groups = dbURLRegex.toRegex().matchEntire(dbURL)!!.groups

        val dataSource = ComboPooledDataSource()
        dataSource.jdbcUrl = "jdbc:postgresql://${groups["domain"]!!.value}"
        dataSource.user = groups["username"]!!.value
        dataSource.password = groups["password"]!!.value
        dataSource.driverClass = "org.postgresql.Driver"

        return dataSource
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(true)
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan("com.lukeshay.discord.domain")
        factory.dataSource = dataSource()
        return factory
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager? {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}
