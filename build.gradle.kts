plugins {
    base
}

group = "dev.nexolith"
version = "0.0.1-SNAPSHOT"

allprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}
