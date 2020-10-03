group = "com.planningwisely"
version = "1.0.0-SNAPSHOT.1"

plugins {
    kotlin("jvm") version "1.4.10" apply false
    kotlin("plugin.serialization") version "1.4.10"
    id("io.gitlab.arturbosch.detekt") version "1.12.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.0"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }

    detekt {
        toolVersion = "1.12.0"
        input = files("src/main/kotlin", "gensrc/main/kotlin")
        config = files("../config/detekt/detekt.yml")
        parallel = false
        buildUponDefaultConfig = true
        disableDefaultRuleSets = false
        debug = false
        ignoreFailures = false
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }

    ktlint {
        outputColorName.set("GREEN")
        enableExperimentalRules.set(true)
        additionalEditorconfigFile.set(file("..\\.editorconfig"))
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}
