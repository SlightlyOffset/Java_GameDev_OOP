package core;

import com.badlogic.gdx.utils.Json;
import core.mechanics.Grid;
import core.mechanics.LevelLoader;
import core.mechanics.Tile;
import core.mechanics.TileType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JSON serialization and deserialization.
 * Tests tile and grid serialization to/from JSON format.
 */
public class JsonSerializationTest {

    @Test
    public void testTileSerialization() {
        Json json = new Json();
        Tile tile = new Tile(TileType.STRAIGHT);
        tile.rotateClockwise(); // 90 degrees

        String jsonString = json.toJson(tile);
        Tile deserializedTile = json.fromJson(Tile.class, jsonString);

        assertEquals(tile.getType(), deserializedTile.getType());
        assertEquals(tile.getRotation(), deserializedTile.getRotation());
    }

    @Test
    public void testGridSerialization() {
        Grid grid = new Grid(2, 2);
        Tile[][] tiles = new Tile[2][2];
        tiles[0][0] = new Tile(TileType.STRAIGHT);
        tiles[0][0].rotateClockwise(); // 90
        tiles[0][1] = new Tile(TileType.L_TURN);
        tiles[0][1].rotateClockwise(); // 90
        tiles[0][1].rotateClockwise(); // 180
        tiles[1][0] = new Tile(TileType.T_JUNCTION);
        tiles[1][1] = new Tile(TileType.CROSS);
        grid.setTiles(tiles);

        String jsonString = LevelLoader.toJson(grid);
        Grid deserializedGrid = new Json().fromJson(Grid.class, jsonString);

        assertEquals(grid.getCols(), deserializedGrid.getCols());
        assertEquals(grid.getRows(), deserializedGrid.getRows());
        assertNotNull(deserializedGrid.getTiles());
        
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                assertEquals(grid.getTiles()[y][x].getType(), deserializedGrid.getTiles()[y][x].getType());
                assertEquals(grid.getTiles()[y][x].getRotation(), deserializedGrid.getTiles()[y][x].getRotation());
            }
        }
    }
}
