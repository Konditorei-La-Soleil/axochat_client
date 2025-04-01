plugins {
    kotlin("jvm") version "2.1.20"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    kotlin {
        jvmToolchain(8)
    }
}

group = "io.github.kls"
version = "0.1.0"
