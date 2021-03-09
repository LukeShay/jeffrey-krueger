import com.lukeshay.discord.gradle.passSystemProperties
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val hibernateVersion = "6.0.0.Alpha6"
val log4jVersion = "2.14.0"
val exposedVersion = "0.29.1"
val kotestVersion = "4.4.3"

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
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    }

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")

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
    implementation("joda-time:joda-time:2.10.10")

    // Hibernate dependencies
    implementation("org.hibernate.orm:hibernate-core:$hibernateVersion")
    implementation("com.mchange:c3p0:0.9.5.5")

    // Postgres dependencies
    implementation("org.postgresql:postgresql:42.2.19")

    // Test dependencies
    // Kotest dependencies
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")

    // Mockk dependencies
    testImplementation("io.mockk:mockk:1.10.6")

    // Kotlin dependencies
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
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