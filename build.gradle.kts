plugins {
    kotlin("jvm") version "2.0.0" // latest stable Kotlin
    kotlin("plugin.spring") version "2.0.0"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.flywaydb.flyway") version "11.10.1"
}

group = "com.greg.respiroc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter")

    // Kotlin Support
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // PostgreSQL Driver
    runtimeOnly("org.postgresql:postgresql:42.7.3")

    // Flyway for DB migrations
    implementation("org.flywaydb:flyway-core:11.10.1")
    implementation("org.flywaydb:flyway-database-postgresql:11.10.1") // Required for PostgreSQL 16+


    // HTMX frontend support (HTMX is used in templates)
    implementation("org.webjars.npm:htmx.org:1.9.12")

    // Web Awesome (for UI components)
    implementation("org.webjars:font-awesome:6.7.2")

    // Scheduler
    implementation("org.springframework.boot:spring-boot-starter")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
