/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.loader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Original Nexolith bootstrap entry point.
 *
 * <p>A launcher places this JAR and the authorized Minecraft runtime on the same class path, then
 * selects this class as the main class. Nexolith initializes its own lifecycle before delegating to
 * the vanilla entry point. The bootstrap does not contain, download, or redistribute Minecraft.</p>
 */
public final class NexolithBootstrap {
    public static final String GAME_MAIN_CLASS_PROPERTY = "nexolith.gameMainClass";
    public static final String DEFAULT_GAME_MAIN_CLASS = "net.minecraft.client.main.Main";

    private NexolithBootstrap() {
    }

    public static void main(String[] args) {
        launch(args, Thread.currentThread().getContextClassLoader());
    }

    static void launch(String[] args, ClassLoader classLoader) {
        String loaderVersion = implementationVersion();
        String gameMainClass = System.getProperty(GAME_MAIN_CLASS_PROPERTY, DEFAULT_GAME_MAIN_CLASS);

        if (NexolithBootstrap.class.getName().equals(gameMainClass)) {
            throw new IllegalArgumentException("Nexolith cannot delegate to its own bootstrap class");
        }

        LaunchContext context = new LaunchContext(loaderVersion, gameMainClass, args);
        NexolithLoader.log("Starting Nexolith Loader " + loaderVersion);
        int extensionCount = NexolithLoader.initialize(context, classLoader);
        NexolithLoader.log("Loader initialized with " + extensionCount + " extension(s)");
        NexolithLoader.log("Delegating to " + gameMainClass);

        invokeGameMain(gameMainClass, context.gameArguments(), classLoader);
    }

    private static void invokeGameMain(String className, String[] args, ClassLoader classLoader) {
        final Class<?> mainClass;
        final Method mainMethod;

        try {
            mainClass = Class.forName(className, true, classLoader);
            mainMethod = mainClass.getMethod("main", String[].class);
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException(
                    "Minecraft main class was not found. The launcher must provide the authorized game JAR and its libraries.",
                    exception);
        } catch (NoSuchMethodException exception) {
            throw new IllegalStateException("Target class does not expose main(String[]): " + className, exception);
        }

        if (!Modifier.isPublic(mainMethod.getModifiers()) || !Modifier.isStatic(mainMethod.getModifiers())) {
            throw new IllegalStateException("Target main method must be public and static: " + className);
        }

        try {
            mainMethod.invoke(null, (Object) args);
        } catch (IllegalAccessException exception) {
            throw new IllegalStateException("Unable to invoke Minecraft main class: " + className, exception);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            if (cause instanceof Error error) {
                throw error;
            }
            throw new IllegalStateException("Minecraft terminated during startup", cause);
        }
    }

    private static String implementationVersion() {
        String version = NexolithBootstrap.class.getPackage().getImplementationVersion();
        return version == null || version.isBlank() ? "development" : version;
    }
}
