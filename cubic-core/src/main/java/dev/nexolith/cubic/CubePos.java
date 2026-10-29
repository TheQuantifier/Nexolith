/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.cubic;

/** Immutable three-dimensional cube position with no column ownership. */
public record CubePos(int x, int y, int z) {
    public static CubePos fromBlock(int blockX, int blockY, int blockZ) {
        return new CubePos(
                CubeGeometry.cubeCoordinate(blockX),
                CubeGeometry.cubeCoordinate(blockY),
                CubeGeometry.cubeCoordinate(blockZ));
    }

    public int minBlockX() {
        return CubeGeometry.blockCoordinate(x, 0);
    }

    public int minBlockY() {
        return CubeGeometry.blockCoordinate(y, 0);
    }

    public int minBlockZ() {
        return CubeGeometry.blockCoordinate(z, 0);
    }

    public int maxBlockX() {
        return CubeGeometry.blockCoordinate(x, CubeGeometry.EDGE_MASK);
    }

    public int maxBlockY() {
        return CubeGeometry.blockCoordinate(y, CubeGeometry.EDGE_MASK);
    }

    public int maxBlockZ() {
        return CubeGeometry.blockCoordinate(z, CubeGeometry.EDGE_MASK);
    }
}
