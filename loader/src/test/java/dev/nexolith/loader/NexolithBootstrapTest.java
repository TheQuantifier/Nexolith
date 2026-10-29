/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class NexolithBootstrapTest {
    @AfterEach
    void clearProperties() {
        System.clearProperty(NexolithBootstrap.GAME_MAIN_CLASS_PROPERTY);
        System.clearProperty("nexolith.loader.active");
        System.clearProperty("nexolith.loader.version");
        System.clearProperty("nexolith.test.gameStarted");
        System.clearProperty("nexolith.test.gameArguments");
    }

    @Test
    void initializesNexolithBeforeDelegatingToTheGame() {
        System.setProperty(NexolithBootstrap.GAME_MAIN_CLASS_PROPERTY, FakeMinecraftMain.class.getName());

        NexolithBootstrap.launch(new String[] {"--username", "Player"}, getClass().getClassLoader());

        assertEquals("true", System.getProperty("nexolith.loader.active"));
        assertEquals("true", System.getProperty("nexolith.test.gameStarted"));
        assertEquals("--username|Player", System.getProperty("nexolith.test.gameArguments"));
    }
}
