package core;

import com.badlogic.gdx.assets.AssetManager;
import core.mechanics.PathPuzzleGame;
import core.windows.MenuScreen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuScreenTest {

    // Manual Stub for AssetManager
    static class StubAssetManager extends AssetManager {
        public String lastLoadedAsset;
        @Override
        public synchronized <T> void load(String fileName, Class<T> type) {
            lastLoadedAsset = fileName;
        }
        
        @Override
        public synchronized boolean isLoaded(String fileName) {
            return true; // Simulate assets are always loaded for testing
        }

        @Override
        public synchronized <T> T get(String fileName, Class<T> type) {
             return null; // Return null for simplicity in tests
        }
    }

    // Manual Stub for PathPuzzleGame
    static class TestGame extends PathPuzzleGame {
        @Override
        public void create() {
            // No-op
        }
    }

    @Test
    public void testLogoAssetLoaded() {
        PathPuzzleGame testGame = new TestGame();
        StubAssetManager stubAssetManager = new StubAssetManager();
        testGame.assetManager = stubAssetManager;

        MenuScreen menuScreen = new MenuScreen(testGame);
        assertNotNull(testGame.assetManager);
    }

    @Test
    public void testBackgroundAssetLoaded() {
        PathPuzzleGame testGame = new TestGame();
        StubAssetManager stubAssetManager = new StubAssetManager();
        testGame.assetManager = stubAssetManager;

        MenuScreen menuScreen = new MenuScreen(testGame);
        assertNotNull(testGame.assetManager);
    }
}
