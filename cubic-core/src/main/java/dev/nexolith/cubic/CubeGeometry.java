/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.cubic;

/** Defines the initial, versioned geometry of a Nexolith world cube. */
public final class CubeGeometry {
    public static final int EDGE_BITS = 4;
    public static final int EDGE_LENGTH = 1 << EDGE_BITS;
    public static final int EDGE_MASK = EDGE_LENGTH - 1;
    public static final int VOLUME = EDGE_LENGTH * EDGE_LENGTH * EDGE_LENGTH;

    private CubeGeometry() {
    }

    public static int cubeCoordinate(int blockCoordinate) {
        return Math.floorDiv(blockCoordinate, EDGE_LENGTH);
    }

    public static int localCoordinate(int blockCoordinate) {
        return Math.floorMod(blockCoordinate, EDGE_LENGTH);
    }

    public static int blockCoordinate(int cubeCoordinate, int localCoordinate) {
        requireLocalCoordinate(localCoordinate);
        return Math.addExact(Math.multiplyExact(cubeCoordinate, EDGE_LENGTH), localCoordinate);
    }

    /** Packs local X, Y and Z into a dense index with X changing fastest, then Z, then Y. */
    public static int localIndex(int localX, int localY, int localZ) {
        requireLocalCoordinate(localX);
        requireLocalCoordinate(localY);
        requireLocalCoordinate(localZ);
        return localX | (localZ << EDGE_BITS) | (localY << (EDGE_BITS * 2));
    }

    public static int localX(int index) {
        requireIndex(index);
        return index & EDGE_MASK;
    }

    public static int localY(int index) {
        requireIndex(index);
        return index >> (EDGE_BITS * 2) & EDGE_MASK;
    }

    public static int localZ(int index) {
        requireIndex(index);
        return index >> EDGE_BITS & EDGE_MASK;
    }

    private static void requireLocalCoordinate(int coordinate) {
        if (coordinate < 0 || coordinate >= EDGE_LENGTH) {
            throw new IllegalArgumentException("Local coordinate must be between 0 and " + EDGE_MASK);
        }
    }

    private static void requireIndex(int index) {
        if (index < 0 || index >= VOLUME) {
            throw new IllegalArgumentException("Cube index must be between 0 and " + (VOLUME - 1));
        }
    }
}
