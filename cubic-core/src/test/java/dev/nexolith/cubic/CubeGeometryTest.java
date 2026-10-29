/*
 * SPDX-License-Identifier: Apache-2.0
 */
package dev.nexolith.cubic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CubeGeometryTest {
    @Test
    void mapsPositiveAndNegativeBlocksWithoutTruncationErrors() {
        assertEquals(new CubePos(0, 0, 0), CubePos.fromBlock(0, 0, 0));
        assertEquals(new CubePos(0, 0, 0), CubePos.fromBlock(15, 15, 15));
        assertEquals(new CubePos(1, 1, 1), CubePos.fromBlock(16, 16, 16));
        assertEquals(new CubePos(-1, -1, -1), CubePos.fromBlock(-1, -1, -1));
        assertEquals(new CubePos(-1, -1, -1), CubePos.fromBlock(-16, -16, -16));
        assertEquals(new CubePos(-2, -2, -2), CubePos.fromBlock(-17, -17, -17));
        assertEquals(15, CubeGeometry.localCoordinate(-1));
        assertEquals(0, CubeGeometry.localCoordinate(-16));
    }

    @Test
    void localIndexIsABijectionAcrossTheCube() {
        boolean[] visited = new boolean[CubeGeometry.VOLUME];
        for (int y = 0; y < CubeGeometry.EDGE_LENGTH; y++) {
            for (int z = 0; z < CubeGeometry.EDGE_LENGTH; z++) {
                for (int x = 0; x < CubeGeometry.EDGE_LENGTH; x++) {
                    int index = CubeGeometry.localIndex(x, y, z);
                    assertEquals(false, visited[index]);
                    visited[index] = true;
                    assertEquals(x, CubeGeometry.localX(index));
                    assertEquals(y, CubeGeometry.localY(index));
                    assertEquals(z, CubeGeometry.localZ(index));
                }
            }
        }
    }

    @Test
    void cubeBoundsRemainThreeDimensional() {
        CubePos cube = new CubePos(-2, 4, 7);
        assertEquals(-32, cube.minBlockX());
        assertEquals(-17, cube.maxBlockX());
        assertEquals(64, cube.minBlockY());
        assertEquals(79, cube.maxBlockY());
        assertEquals(112, cube.minBlockZ());
        assertEquals(127, cube.maxBlockZ());
    }

    @Test
    void rejectsCoordinatesAndIndicesOutsideOneCube() {
        assertThrows(IllegalArgumentException.class, () -> CubeGeometry.localIndex(-1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> CubeGeometry.localIndex(16, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> CubeGeometry.localX(CubeGeometry.VOLUME));
    }
}
