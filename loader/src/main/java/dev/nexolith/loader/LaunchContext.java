/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.loader;

import java.util.Arrays;
import java.util.Objects;

/** Information available to Nexolith extensions before Minecraft starts. */
public record LaunchContext(String loaderVersion, String gameMainClass, String[] gameArguments) {
    public LaunchContext {
        Objects.requireNonNull(loaderVersion, "loaderVersion");
        Objects.requireNonNull(gameMainClass, "gameMainClass");
        Objects.requireNonNull(gameArguments, "gameArguments");
        gameArguments = Arrays.copyOf(gameArguments, gameArguments.length);
    }

    @Override
    public String[] gameArguments() {
        return Arrays.copyOf(gameArguments, gameArguments.length);
    }
}
