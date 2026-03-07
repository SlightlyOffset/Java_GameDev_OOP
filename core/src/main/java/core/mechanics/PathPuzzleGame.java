package core.mechanics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import core.windows.GameScreen;
import core.windows.MenuScreen;

public class PathPuzzleGame extends Game {
    public AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();

        // Preload assets
        assetManager.load("Logo.png", Texture.class);
        // assetManager.load("background.png", Texture.class);
        // Note: We need a skin for UI buttons. Using a placeholder for now if possible.
        // For actual implementation, we might need to load a .json skin or create one manually.
        
        assetManager.finishLoading();

        setScreen(new MenuScreen(this)); // Pass the game instance to MenuScreen
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose(); // CRITICAL: Dispose of assets -> prevents memory leaks
    }
}
