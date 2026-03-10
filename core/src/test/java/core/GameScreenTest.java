package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import core.mechanics.PathPuzzleGame;
import core.windows.GameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class GameScreenTest {

    @BeforeEach
    public void setup() {
        Gdx.gl = mock(GL20.class);
        Gdx.gl20 = Gdx.gl;
    }

    // Manual Stub for PathPuzzleGame
    static class TestGame extends PathPuzzleGame {
        @Override
        public void create() {
            // No-op
        }
    }

    @Test
    public void testGameScreenInstantiation() {
        PathPuzzleGame testGame = new TestGame();
        // We use null for levelPath to test the default random init
        GameScreen gameScreen = new GameScreen(testGame);
        assertNotNull(gameScreen);
    }

    @Test
    public void testGameScreenWithLevelPath() {
        PathPuzzleGame testGame = new TestGame();
        // Since we are in a test, the path needs to be correct relative to core/
        GameScreen gameScreen = new GameScreen(testGame, "../assets/levels/level_1.json");
        assertNotNull(gameScreen);
    }
}
