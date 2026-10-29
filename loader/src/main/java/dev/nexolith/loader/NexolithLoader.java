/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.loader;

import java.util.HashSet;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.regex.Pattern;

/** Owns the first Nexolith-controlled lifecycle that runs before Minecraft. */
public final class NexolithLoader {
    private static final Pattern EXTENSION_ID = Pattern.compile("[a-z][a-z0-9_-]{1,63}");

    private NexolithLoader() {
    }

    static int initialize(LaunchContext context, ClassLoader classLoader) {
        System.setProperty("nexolith.loader.active", "true");
        System.setProperty("nexolith.loader.version", context.loaderVersion());

        Set<String> extensionIds = new HashSet<>();
        int loaded = 0;

        try {
            for (LoaderExtension extension : ServiceLoader.load(LoaderExtension.class, classLoader)) {
                String id = extension.id();
                if (id == null || !EXTENSION_ID.matcher(id).matches()) {
                    throw new IllegalStateException("Invalid Nexolith extension id: " + id);
                }
                if (!extensionIds.add(id)) {
                    throw new IllegalStateException("Duplicate Nexolith extension id: " + id);
                }

                try {
                    extension.initialize(context);
                } catch (Exception exception) {
                    throw new IllegalStateException("Nexolith extension failed to initialize: " + id, exception);
                }
                loaded++;
                log("Initialized extension " + id);
            }
        } catch (ServiceConfigurationError error) {
            throw new IllegalStateException("Unable to discover Nexolith extensions", error);
        }

        return loaded;
    }

    static void log(String message) {
        System.err.println("[Nexolith] " + message);
    }
}
