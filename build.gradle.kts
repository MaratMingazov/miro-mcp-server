plugins {
    val kotlinVersion = "2.0.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation("org.springframework.ai:spring-ai-starter-mcp-server:1.0.0-SNAPSHOT")
    implementation("io.modelcontextprotocol:kotlin-sdk:0.4.0")
    implementation("org.springframework:spring-web")
}

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven {
            name = "Central Portal Snapshots"
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}