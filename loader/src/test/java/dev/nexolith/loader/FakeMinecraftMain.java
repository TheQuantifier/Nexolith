/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.loader;

import java.util.Arrays;

public final class FakeMinecraftMain {
    private FakeMinecraftMain() {
    }

    public static void main(String[] args) {
        System.setProperty("nexolith.test.gameStarted", "true");
        System.setProperty("nexolith.test.gameArguments", String.join("|", Arrays.asList(args)));
    }
}
