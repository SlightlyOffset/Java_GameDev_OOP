package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PathPuzzleGameTest {
    @Test
    public void testGameInitialization() {
        PathPuzzleGame game = new PathPuzzleGame();
        assertNotNull(game);
    }
}
