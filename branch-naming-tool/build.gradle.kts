import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories { jcenter() }
    dependencies { classpath("org.koin:koin-gradle-plugin:2.1.6") }
}

val koinVersion = "2.1.6"
val kotestVersion = "4.2.5"

apply(plugin = "koin")
plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "6.0.0"
    application
    java
}

group = "com.planningwisely.branchtool"
version = "1.0.0-SNAPSHOT.1"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":generic"))
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0-RC2")
    testImplementation(kotlin("test-junit5"))

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")

    implementation("org.koin:koin-core:$koinVersion")
    implementation("org.koin:koin-core-ext:$koinVersion")
    testImplementation("org.koin:koin-test:$koinVersion")

    testImplementation("io.mockk:mockk:1.10.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClassName = "EntryPointKt"
}
