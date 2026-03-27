package core;

import core.mechanics.Grid;
import core.mechanics.LevelLoader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LevelLoader class.
 * Tests JSON level file loading and grid deserialization.
 */
public class LevelLoaderTest {

    @Test
    public void testLoadLevel() {
        // level_1.json exists in assets/levels/
        // When running from :core:test, the working directory is core/
        Grid grid = LevelLoader.loadLevel("../assets/levels/level_1.json");
        
        assertNotNull(grid);
        assertEquals(3, grid.getCols());
        assertEquals(3, grid.getRows());
        assertNotNull(grid.getTiles());
    }
}
