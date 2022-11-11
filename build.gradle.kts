import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    id("com.ryandens.javaagent-application") version "0.4.0"
    application
}

group = "com.swi.str"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    javaagent("com.appoptics.agent.java:appoptics-agent:6.24.3")
    implementation("io.projectreactor.kafka:reactor-kafka:1.3.13")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}