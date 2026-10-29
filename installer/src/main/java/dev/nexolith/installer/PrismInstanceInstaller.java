/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.installer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/** Installs a local Prism Launcher instance that starts Minecraft through Nexolith. */
public final class PrismInstanceInstaller {
    static final String LOADER_VERSION = "0.0.1-SNAPSHOT";

    private PrismInstanceInstaller() {
    }

    public static void main(String[] arguments) throws IOException {
        Map<String, String> options = parseArguments(arguments);
        Path prismRoot = options.containsKey("--prism-root")
                ? Path.of(options.get("--prism-root"))
                : defaultPrismRoot();
        Path loaderJar = requiredPath(options, "--loader-jar");
        Path agentJar = requiredPath(options, "--agent-jar");
        Path icon = requiredPath(options, "--icon");
        String minecraftVersion = options.getOrDefault("--minecraft-version", "26.3");
        String instanceName = options.getOrDefault("--instance-name", "Nexolith Phase 1");

        Path installed = install(prismRoot, loaderJar, agentJar, icon, minecraftVersion, instanceName);
        System.out.println("Nexolith Prism instance installed at:");
        System.out.println(installed.toAbsolutePath());
        System.out.println("Restart Prism Launcher if it is open, then launch '" + instanceName + "'.");
    }

    static Path install(
            Path prismRoot,
            Path loaderJar,
            Path agentJar,
            Path icon,
            String minecraftVersion,
            String instanceName) throws IOException {
        requireRegularFile(loaderJar, "Nexolith loader JAR");
        requireRegularFile(agentJar, "Nexolith transformer JAR");
        requireRegularFile(icon, "Nexolith icon");
        requireSimpleValue(minecraftVersion, "Minecraft version");
        requireSimpleValue(instanceName, "instance name");

        Path instancesRoot = resolveInstancesRoot(prismRoot);
        if (!Files.isDirectory(instancesRoot)) {
            throw new IOException("Prism Launcher instances folder was not found: " + instancesRoot);
        }

        String folderName = safeFolderName(instanceName);
        Path target = instancesRoot.resolve(folderName);
        if (Files.exists(target)) {
            throw new IOException("A Prism instance already exists at " + target
                    + ". Remove it in Prism or choose another -PinstanceName.");
        }

        Path staging = Files.createTempDirectory(instancesRoot, ".nexolith-install-");
        boolean installed = false;
        try {
            Files.createDirectories(staging.resolve("patches"));
            Files.createDirectories(staging.resolve("libraries"));
            Files.createDirectories(staging.resolve("minecraft"));

            write(staging.resolve("instance.cfg"), instanceConfiguration(instanceName));
            write(staging.resolve("mmc-pack.json"), packMetadata(minecraftVersion));
            Path installedAgent = target.resolve("libraries/nexolith-transformer-" + LOADER_VERSION + ".jar");
            write(staging.resolve("patches/dev.nexolith.json"),
                    componentMetadata(minecraftVersion, installedAgent));
            Files.copy(loaderJar, staging.resolve("libraries/nexolith-loader-" + LOADER_VERSION + ".jar"));
            Files.copy(agentJar, staging.resolve("libraries/nexolith-transformer-" + LOADER_VERSION + ".jar"));
            Files.copy(icon, staging.resolve("nexolith.png"));

            Files.move(staging, target, StandardCopyOption.ATOMIC_MOVE);
            installed = true;
            return target;
        } finally {
            if (!installed) {
                deleteTree(staging);
            }
        }
    }

    private static Map<String, String> parseArguments(String[] arguments) {
        Map<String, String> result = new LinkedHashMap<>();
        if (arguments.length % 2 != 0) {
            throw new IllegalArgumentException("Every installer option must have a value");
        }
        for (int index = 0; index < arguments.length; index += 2) {
            String option = arguments[index];
            if (!option.startsWith("--")) {
                throw new IllegalArgumentException("Unknown installer argument: " + option);
            }
            result.put(option, arguments[index + 1]);
        }
        return result;
    }

    private static Path requiredPath(Map<String, String> options, String name) {
        String value = options.get(name);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing required option " + name);
        }
        return Path.of(value);
    }

    private static Path defaultPrismRoot() {
        String operatingSystem = System.getProperty("os.name", "").toLowerCase();
        if (operatingSystem.contains("win")) {
            String appData = System.getenv("APPDATA");
            if (appData != null && !appData.isBlank()) {
                return Path.of(appData, "PrismLauncher");
            }
        }
        if (operatingSystem.contains("mac")) {
            return Path.of(System.getProperty("user.home"), "Library", "Application Support", "PrismLauncher");
        }
        return Path.of(System.getProperty("user.home"), ".local", "share", "PrismLauncher");
    }

    private static String instanceConfiguration(String instanceName) {
        return "[General]\n"
                + "ConfigVersion=1.3\n"
                + "InstanceType=OneSix\n"
                + "MCLaunchMethod=LauncherPart\n"
                + "AutomaticJava=true\n"
                + "iconKey=nexolith\n"
                + "uuid=" + UUID.randomUUID().toString().replace("-", "") + "\n"
                + "name=" + instanceName + "\n";
    }

    private static Path resolveInstancesRoot(Path prismRoot) throws IOException {
        Path configuration = prismRoot.resolve("prismlauncher.cfg");
        if (Files.isRegularFile(configuration)) {
            for (String line : Files.readAllLines(configuration, StandardCharsets.UTF_8)) {
                if (line.startsWith("InstanceDir=")) {
                    String configured = line.substring("InstanceDir=".length()).trim();
                    if (configured.startsWith("\"") && configured.endsWith("\"")
                            && configured.length() >= 2) {
                        configured = configured.substring(1, configured.length() - 1);
                    }
                    if (!configured.isBlank()) {
                        Path path = Path.of(configured);
                        return path.isAbsolute() ? path : prismRoot.resolve(path).normalize();
                    }
                }
            }
        }
        return prismRoot.resolve("instances");
    }

    private static String packMetadata(String minecraftVersion) {
        return """
                {
                  "formatVersion": 1,
                  "components": [
                    {
                      "important": true,
                      "uid": "net.minecraft",
                      "version": "%s"
                    },
                    {
                      "uid": "dev.nexolith",
                      "version": "%s"
                    }
                  ]
                }
                """.formatted(minecraftVersion, LOADER_VERSION);
    }

    private static String componentMetadata(String minecraftVersion, Path installedAgent) {
        String agentArgument = "-javaagent:" + installedAgent.toAbsolutePath().toString().replace('\\', '/');
        return """
                {
                  "formatVersion": 1,
                  "name": "Nexolith Loader",
                  "uid": "dev.nexolith",
                  "version": "%s",
                  "type": "release",
                  "requires": [
                    {
                      "equals": "%s",
                      "uid": "net.minecraft"
                    }
                  ],
                  "mainClass": "dev.nexolith.loader.NexolithBootstrap",
                  "+jvmArgs": [
                    "%s"
                  ],
                  "libraries": [
                    {
                      "MMC-hint": "local",
                      "name": "dev.nexolith:nexolith-loader:%s"
                    }
                  ]
                }
                """.formatted(LOADER_VERSION, minecraftVersion, agentArgument, LOADER_VERSION);
    }

    private static void write(Path path, String contents) throws IOException {
        Files.writeString(path, contents, StandardCharsets.UTF_8);
    }

    private static void requireRegularFile(Path path, String label) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new IOException(label + " was not found: " + path);
        }
    }

    private static void requireSimpleValue(String value, String label) {
        if (value == null || value.isBlank() || value.contains("\n") || value.contains("\r")) {
            throw new IllegalArgumentException("Invalid " + label);
        }
    }

    private static String safeFolderName(String instanceName) {
        String safe = instanceName.trim().replaceAll("[<>:\"/\\\\|?*]", "-");
        safe = safe.replaceAll("[. ]+$", "");
        if (safe.isBlank()) {
            throw new IllegalArgumentException("Instance name does not contain a valid folder name");
        }
        return safe;
    }

    private static void deleteTree(Path root) throws IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (var paths = Files.walk(root)) {
            for (Path path : paths.sorted((left, right) -> right.compareTo(left)).toList()) {
                Files.deleteIfExists(path);
            }
        }
    }
}
