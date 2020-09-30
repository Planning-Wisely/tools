import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    application
    java
}

group = "com.planningwisely"
version = "1.0.0-SNAPSHOT.1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(project(":generic"))
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0-RC2")
    testImplementation(kotlin("test-junit5"))
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "EntryPointKt"
}
