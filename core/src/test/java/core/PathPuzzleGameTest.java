package core;

import core.mechanics.PathPuzzleGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathPuzzleGameTest {

    @Test
    public void testLevelListIsCorrect() {
        PathPuzzleGame game = new PathPuzzleGame() {
            @Override
            public void create() {} // Avoid full LibGDX setup
        };
        
        String[] expectedLevels = {"level_1.json", "level_2.json", "level_3.json"};
        assertArrayEquals(expectedLevels, game.LEVELS);
        assertEquals("levels/", game.LEVEL_PATH);
    }
}
