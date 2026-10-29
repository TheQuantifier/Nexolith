plugins {
    application
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "dev.nexolith.installer.PrismInstanceInstaller"
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
    archiveBaseName = "nexolith-prism-installer"
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get(),
            "Implementation-Title" to "Nexolith Prism Installer",
            "Implementation-Version" to project.version
        )
    }
}

val loaderJarFile = rootProject.layout.projectDirectory.file(
    "loader/build/libs/nexolith-loader-${project.version}.jar"
)
val transformerJarFile = rootProject.layout.projectDirectory.file(
    "transformer/build/libs/nexolith-transformer-${project.version}.jar"
)

tasks.register<JavaExec>("installPrismInstance") {
    group = "nexolith"
    description = "Builds Nexolith and installs a Prism Launcher instance."
    dependsOn(tasks.named("classes"), ":loader:jar", ":transformer:jar")
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = application.mainClass

    val argsList = mutableListOf(
        "--loader-jar", loaderJarFile.asFile.absolutePath,
        "--agent-jar", transformerJarFile.asFile.absolutePath,
        "--icon", rootProject.file("assets/nexolith-icon.png").absolutePath,
        "--minecraft-version", providers.gradleProperty("minecraftVersion").getOrElse("26.3"),
        "--instance-name", providers.gradleProperty("instanceName").getOrElse("Nexolith Phase 1")
    )
    providers.gradleProperty("prismRoot").orNull?.let {
        argsList.addAll(listOf("--prism-root", it))
    }
    args(argsList)
}
