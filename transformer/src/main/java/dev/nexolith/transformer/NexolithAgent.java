/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.transformer;

import dev.nexolith.cubic.CubeGeometry;
import dev.nexolith.cubic.CubePos;
import java.lang.instrument.Instrumentation;

/** JVM entry point for Nexolith's original pre-Minecraft transformation layer. */
public final class NexolithAgent {
    private NexolithAgent() {
    }

    public static void premain(String arguments, Instrumentation instrumentation) {
        System.setProperty("nexolith.transformer.active", "true");
        System.setProperty("nexolith.cubic.active", "true");
        System.setProperty("nexolith.cubic.edgeLength", Integer.toString(CubeGeometry.EDGE_LENGTH));

        CubePos diagnosticCube = CubePos.fromBlock(-1, 64, 32);
        System.err.println("[Nexolith] Native cube foundation active: edge="
                + CubeGeometry.EDGE_LENGTH + ", volume=" + CubeGeometry.VOLUME
                + ", diagnosticCube=" + diagnosticCube);

        instrumentation.addTransformer(new MinecraftTitleTransformer());
        instrumentation.addTransformer(new TitleScreenLoaderTransformer());
        System.err.println("[Nexolith] Runtime transformer installed");
    }
}
