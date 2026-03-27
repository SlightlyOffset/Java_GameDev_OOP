package core;

import core.mechanics.Tile;
import core.mechanics.TileType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tile class.
 * Tests tile rotation mechanics (clockwise and counterclockwise) and tile types.
 */
public class TileTest {

    @Test
    public void testTileRotationClockwise() {
        Tile tile = new Tile(TileType.STRAIGHT);
        assertEquals(0, tile.getRotation());

        tile.rotateClockwise();
        assertEquals(90, tile.getRotation());

        tile.rotateClockwise();
        assertEquals(180, tile.getRotation());

        tile.rotateClockwise();
        assertEquals(270, tile.getRotation());

        tile.rotateClockwise();
        assertEquals(0, tile.getRotation());
    }

    @Test
    public void testTileRotationCounterClockwise() {
        Tile tile = new Tile(TileType.L_TURN);
        assertEquals(0, tile.getRotation());

        tile.rotateCounterClockwise();
        assertEquals(270, tile.getRotation());

        tile.rotateCounterClockwise();
        assertEquals(180, tile.getRotation());

        tile.rotateCounterClockwise();
        assertEquals(90, tile.getRotation());

        tile.rotateCounterClockwise();
        assertEquals(0, tile.getRotation());
    }

    @Test
    public void testTileType() {
        Tile tile = new Tile(TileType.CROSS);
        assertEquals(TileType.CROSS, tile.getType());
    }
}
