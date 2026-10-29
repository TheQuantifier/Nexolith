/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.installer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PrismInstanceInstallerTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void installsSelfContainedPrismComponent() throws IOException {
        Path prismRoot = temporaryDirectory.resolve("PrismLauncher");
        Files.createDirectories(prismRoot.resolve("instances"));
        Path loaderJar = Files.writeString(temporaryDirectory.resolve("loader.jar"), "loader");
        Path agentJar = Files.writeString(temporaryDirectory.resolve("agent.jar"), "agent");
        Path icon = Files.writeString(temporaryDirectory.resolve("icon.png"), "icon");

        Path installed = PrismInstanceInstaller.install(
                prismRoot, loaderJar, agentJar, icon, "26.3", "Nexolith Test");

        assertEquals(prismRoot.resolve("instances/Nexolith Test"), installed);
        assertTrue(Files.isRegularFile(installed.resolve("instance.cfg")));
        assertTrue(Files.readString(installed.resolve("instance.cfg")).startsWith("[General]\n"));
        assertTrue(Files.isRegularFile(installed.resolve("mmc-pack.json")));
        assertTrue(Files.isRegularFile(installed.resolve("patches/dev.nexolith.json")));
        assertTrue(Files.isRegularFile(installed.resolve(
                "libraries/nexolith-loader-0.0.1-SNAPSHOT.jar")));
        assertTrue(Files.isRegularFile(installed.resolve(
                "libraries/nexolith-transformer-0.0.1-SNAPSHOT.jar")));
        assertTrue(Files.readString(installed.resolve("patches/dev.nexolith.json"))
                .contains("dev.nexolith.loader.NexolithBootstrap"));
        assertTrue(Files.readString(installed.resolve("patches/dev.nexolith.json"))
                .contains("\"MMC-hint\": \"local\""));
        assertTrue(Files.readString(installed.resolve("patches/dev.nexolith.json"))
                .contains("-javaagent:"));
    }

    @Test
    void refusesToOverwriteAnExistingInstance() throws IOException {
        Path prismRoot = temporaryDirectory.resolve("PrismLauncher");
        Files.createDirectories(prismRoot.resolve("instances/Nexolith 26.3"));
        Path loaderJar = Files.writeString(temporaryDirectory.resolve("loader.jar"), "loader");
        Path agentJar = Files.writeString(temporaryDirectory.resolve("agent.jar"), "agent");
        Path icon = Files.writeString(temporaryDirectory.resolve("icon.png"), "icon");

        assertThrows(IOException.class, () -> PrismInstanceInstaller.install(
                prismRoot, loaderJar, agentJar, icon, "26.3", "Nexolith 26.3"));
    }

    @Test
    void honorsPrismCustomInstanceDirectory() throws IOException {
        Path prismRoot = temporaryDirectory.resolve("PrismLauncher");
        Path customInstances = temporaryDirectory.resolve("Custom Instances");
        Files.createDirectories(prismRoot);
        Files.createDirectories(customInstances);
        Files.writeString(prismRoot.resolve("prismlauncher.cfg"),
                "InstanceDir=" + customInstances.toString().replace('\\', '/') + "\n");
        Path loaderJar = Files.writeString(temporaryDirectory.resolve("loader.jar"), "loader");
        Path agentJar = Files.writeString(temporaryDirectory.resolve("agent.jar"), "agent");
        Path icon = Files.writeString(temporaryDirectory.resolve("icon.png"), "icon");

        Path installed = PrismInstanceInstaller.install(
                prismRoot, loaderJar, agentJar, icon, "26.3", "Nexolith Custom");

        assertEquals(customInstances.resolve("Nexolith Custom"), installed);
    }
}
