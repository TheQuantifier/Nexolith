plugins {
    `java-library`
    application
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withJavadocJar()
    withSourcesJar()
}

application {
    mainClass = "dev.nexolith.loader.NexolithBootstrap"
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.14.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release = 17
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.named<Jar>("jar") {
    archiveBaseName = "nexolith-loader"
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get(),
            "Implementation-Title" to "Nexolith Loader",
            "Implementation-Version" to project.version
        )
    }
}
