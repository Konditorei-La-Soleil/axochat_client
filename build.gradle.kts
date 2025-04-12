plugins {
    kotlin("jvm") version "2.1.20"
    `maven-publish`
}

group = "io.github.kls"
version = "0.1.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    group = rootProject.group
    version = rootProject.version

    kotlin {
        jvmToolchain(8)
    }

    tasks.jar {
        archiveBaseName.set(this@subprojects.name)
        manifest {
            attributes["Implementation-Title"] = this@subprojects.name
            attributes["Implementation-Version"] = version
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }
        repositories {
            mavenLocal()
        }
    }
}