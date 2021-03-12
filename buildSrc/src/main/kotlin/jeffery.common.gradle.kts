import com.lukeshay.discord.gradle.passSystemProperties
import gradle.kotlin.dsl.accessors._4c137f78e7a759b3056af35daeb34c6d.implementation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val aKeylessVersion: String by project
val c3p0Version: String by project
val exposedVersion: String by project
val hibernateVersion: String by project
val jdaVersion: String by project
val jodaTimeVersion: String by project
val kotestVersion: String by project
val kotlinVersion: String by project
val kotlinxCoroutinesVersion: String by project
val log4jVersion: String by project
val mockkVersion: String by project
val postgresqlVersion: String by project
val sentryVersion: String by project

group = "com.lukeshay.discord"
version = System.getProperty("app.version", "version")

plugins {
    jacoco
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    jcenter()
    jcenter {
        url = uri("https://akeyless.jfrog.io/artifactory/akeyless-java")
    }
}

dependencies {
    // Discord dependencies
    implementation("net.dv8tion:JDA:$jdaVersion") {
        exclude("opus-java")
    }

    implementation("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")

    // Log4j dependencies
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")

    // Exposed dependencies
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")

    // JodaTime dependencies
    implementation("joda-time:joda-time:$jodaTimeVersion")

    // Hibernate dependencies
    implementation("org.hibernate.orm:hibernate-core:$hibernateVersion")
    implementation("com.mchange:c3p0:$c3p0Version")

    // Postgres dependencies
    implementation("org.postgresql:postgresql:$postgresqlVersion")

    // AKEYLESS dependencies
    implementation("io.akeyless:akeyless-java:$aKeylessVersion")

    // Sentry dependencies
    implementation("io.sentry:sentry-log4j2:$sentryVersion")

    // Test dependencies
    // Kotest dependencies
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")

    // Mockk dependencies
    testImplementation("io.mockk:mockk:$mockkVersion")

    // Kotlin dependencies
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")
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
                            "com/lukeshay/discord/logging/**",
                            "com/lukeshay/discord/MainKt.class"
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

    passSystemProperties(this)

    finalizedBy(tasks.jacocoTestReport)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}