plugins {
    java
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(project(":cubic-core"))
    implementation("org.ow2.asm:asm:9.11.1")
    implementation("org.ow2.asm:asm-tree:9.11.1")

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
    dependsOn(":cubic-core:jar")
    archiveBaseName = "nexolith-transformer"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from({
        configurations.runtimeClasspath.get().map { dependency ->
            if (dependency.isDirectory) dependency else zipTree(dependency)
        }
    })
    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    from(rootProject.file("THIRD_PARTY_NOTICES.md")) {
        into("META-INF")
    }
    from(rootProject.file("third-party/ASM-LICENSE.txt")) {
        into("META-INF/licenses")
    }
    manifest {
        attributes(
            "Premain-Class" to "dev.nexolith.transformer.NexolithAgent",
            "Can-Redefine-Classes" to "false",
            "Can-Retransform-Classes" to "false",
            "Implementation-Title" to "Nexolith Runtime Transformer",
            "Implementation-Version" to project.version
        )
    }
}
