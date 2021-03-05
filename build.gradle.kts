import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

val springVersion = "5.3.4"
val hibernateVersion = "6.0.0.Alpha6"
val junit5Version = "5.6.0"
val log4jVersion = "2.14.0"

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.4.31")
    }
}

plugins {
    application
    jacoco
    id("org.jetbrains.kotlin.jvm") version "1.4.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("org.jetbrains.kotlin.plugin.spring") version "1.4.31"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.4.31"
    id("com.heroku.sdk.heroku-gradle") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.lukeshay.discord"
version = System.getProperty("app.version", "version")

repositories {
//    mavenCentral()
    jcenter()
    jcenter {
        url = uri("https://akeyless.jfrog.io/artifactory/akeyless-java")
    }
}

dependencies {
    // Discord dependencies
    implementation("net.dv8tion:JDA:4.2.0_228") {
        exclude("opus-java")
    }

    // Spring dependencies
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-beans:$springVersion")
    implementation("org.springframework.data:spring-data-jpa:2.4.5")

    // Hibernate dependencies
    implementation("org.hibernate.orm:hibernate-core:$hibernateVersion")
    implementation("com.mchange:c3p0:0.9.5.5")

    // Postgres dependencies
    implementation("org.postgresql:postgresql:42.2.19")

    // Kotlin dependencies
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")

    // klaxon dependencies
    implementation("com.beust:klaxon:5.0.1")

    // Log4j dependencies
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")

    // AKEYLESS dependencies
    implementation("io.akeyless:akeyless-java:2.2.1")

    // Test dependencies
    // JUnit5 dependencies
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit5Version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit5Version")

    // Hibernate dependencies
    testImplementation("org.hibernate.orm:hibernate-testing:$hibernateVersion")

    // H2 dependencies
    implementation("com.h2database:h2:1.4.200")

    // Mockito dependencies
    testImplementation("org.mockito:mockito-core:2.+")

    // Spring dependencies
    testImplementation("org.springframework:spring-test:$springVersion")
}

tasks.jacocoTestReport {
    // Adjust the output of the test report
    reports {
        xml.isEnabled = true
        html.isEnabled = true
        csv.isEnabled = true
        html.destination = file("$buildDir/jacocoHtml")
    }
}

tasks.jacocoTestCoverageVerification {
    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(
                    mapOf(
                        "dir" to it,
                        "exclude" to arrayOf(
                            "com/lukeshay/discord/repositories/**",
                            "com/lukeshay/discord/entities/**",
                            "com/lukeshay/discord/config/**",
                            "com/lukeshay/discord/logger/**"
                        )
                    )
                )
            }
        )
    )

    violationRules {
        rule {
            isFailOnViolation = false
            element = "BUNDLE"
            limit {
                minimum = "0.7".toBigDecimal()
            }
        }
        rule {
            isFailOnViolation = false
            element = "SOURCEFILE"
            limit {
                minimum = "0.01".toBigDecimal()
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()

    finalizedBy(tasks.jacocoTestReport)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.shadowJar {
    minimize {
        exclude(dependency(".*:.*:.*"))
        include(dependency("net.dv8tion:.*:.*"))
        include(dependency("org.postgresql:postgresql:.*"))
        include(dependency("org.hibernate.orm:hibernate-core:.*"))
        include(dependency("com.mchange:c3p0:.*"))
    }
}

application {
    mainClass.set("com.lukeshay.discord.MainKt")
    mainClassName = "com.lukeshay.discord.MainKt"
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/style-violations.kt")
    }
}

heroku {
    appName = System.getProperty("heroku.app.name", "jeffery-krueger-dev")
    jdkVersion = "11"
    processTypes =
        mapOf(
            "worker" to "java \$JAVA_OPTS -Ddatabase.url=\$DATABASE_URL -Denvironment=\$ENVIRONMENT -Dakeyless.access.id=\$AKEYLESS_ACCESS_ID -Dakeyless.access.key=\$AKEYLESS_ACCESS_KEY -jar build/libs/jeffery-krueger-$version-all.jar"
        )
    buildpacks = listOf("heroku/jvm")
    includes = listOf("build/libs/jeffery-krueger-$version-all.jar")
    isIncludeBuildDir = false
}
