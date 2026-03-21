package core;

import core.mechanics.Grid;
import core.mechanics.Tile;
import core.mechanics.TileType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    @Test
    public void testDimensions() {
        Grid grid = new Grid(4, 4);
        assertEquals(4, grid.getCols());
        assertEquals(4, grid.getRows());
    }

    @Test
    public void testDifferentDimensions() {
        Grid grid = new Grid(6, 3);
        assertEquals(6, grid.getCols());
        assertEquals(3, grid.getRows());
    }

    @Test
    public void testTilesArrayInitializedByDefault() {
        Grid grid = new Grid(4, 4);
        assertNotNull(grid.getTiles());
        assertEquals(4, grid.getTiles().length);       // rows
        assertEquals(4, grid.getTiles()[0].length);    // cols
    }

    @Test
    public void testSetAndGetTiles() {
        Grid grid = new Grid(4, 4);
        Tile[][] tiles = new Tile[4][4];
        Tile tile = new Tile(TileType.STRAIGHT);
        tiles[2][3] = tile;
        grid.setTiles(tiles);
        assertSame(tile, grid.getTiles()[2][3]);
    }

    @Test
    public void testRandomInitFillsAllTiles() {
        Grid grid = new Grid(4, 4);
        grid.randomInitTile();
        Tile[][] tiles = grid.getTiles();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                assertNotNull(tiles[y][x], "Tile at (" + x + "," + y + ") should not be null after randomInit");
            }
        }
    }

    @Test
    public void testRandomInitTilesHaveValidRotations() {
        Grid grid = new Grid(4, 4);
        grid.randomInitTile();
        Tile[][] tiles = grid.getTiles();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int rotation = tiles[y][x].getRotation();
                assertTrue(
                    rotation == 0 || rotation == 90 || rotation == 180 || rotation == 270,
                    "Tile at (" + x + "," + y + ") has invalid rotation: " + rotation
                );
            }
        }
    }

    @Test
    public void testRandomInitTilesHaveValidTypes() {
        Grid grid = new Grid(4, 4);
        grid.randomInitTile();
        Tile[][] tiles = grid.getTiles();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                assertNotNull(tiles[y][x].getType(), "Tile type at (" + x + "," + y + ") should not be null");
            }
        }
    }

    @Test
    public void testSimpleStraightPath() {
        // สร้าง Grid ขนาด 1x3 (แนวนอน)
        Grid grid = new Grid(3, 1);
        Tile[][] tiles = new Tile[1][3];

        // วาง Tile แบบ STRAIGHT ทั้งหมดและหมุนให้เป็นแนวนอน (90 องศา)
        for (int i = 0; i < 3; i++) {
            tiles[0][i] = new Tile(TileType.STRAIGHT);
            tiles[0][i].rotateClockwise(); // จาก เหนือ-ใต้ เป็น ออก-ตก
        }
        grid.setTiles(tiles);
        assertTrue(grid.isPathComplete());
    }

    @Test
    public void testBlockedPath() {
        Grid grid = new Grid(2, 2);
        Tile[][] tiles = new Tile[2][2];

        // วาง Tile ที่ไม่เชื่อมต่อกัน
        tiles[0][0] = new Tile(TileType.STRAIGHT);
        tiles[0][1] = new Tile(TileType.STRAIGHT);
        tiles[1][0] = new Tile(TileType.STRAIGHT);
        tiles[1][1] = new Tile(TileType.STRAIGHT);

        grid.setTiles(tiles);

        // ต้องคืนค่า false เพราะท่อหันผิดทิศทาง ไม่เกิดเส้นทางจากจุดเริ่มไปจุดจบ
        assertFalse(grid.isPathComplete());
    }
}
