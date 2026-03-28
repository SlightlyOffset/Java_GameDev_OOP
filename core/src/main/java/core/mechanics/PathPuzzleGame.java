package core.mechanics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import core.windows.MenuScreen;

/**
 * The main entry point and controller for the entire game application.
 * It extends the LibGDX {@link Game} class, which manages screen transitions.
 * This class is responsible for managing global resources like the {@link AssetManager}
 * and game-wide settings such as volume.
 */
public class PathPuzzleGame extends Game {
    /** Manages loading and storage of all game assets, such as textures, sounds, and music. */
    public AssetManager assetManager;
    /** Global volume setting for music, ranging from 0.0f to 1.0f. */
    public float musicVolume = 1.0f;
    /** Global volume setting for sound effects, ranging from 0.0f to 1.0f. */
    public float sfxVolume = 1.0f;
    /** The base directory where level JSON files are stored. */
    public static final String LEVEL_PATH = "levels/";
    public static final String[] LEVELS = {"level_1.json", "level_2.json", "level_3.json", "level_4.json"};
    
    public static boolean[] unlockedLevels = new boolean[LEVELS.length];
    /**
     * Called when the application is first created.
     * Initializes the AssetManager, loads all necessary assets, and sets the initial screen to the MenuScreen.
     */
    static {
        unlockedLevels[0] = true;
    }
    @Override
    public void create() {
        assetManager = new AssetManager();

        // Preload assets
        assetManager.load("images/logo.png", Texture.class);
        assetManager.load("images/background.png", Texture.class);
        assetManager.load("images/LevelSelBG.png", Texture.class);
        assetManager.load("sounds/menu_bgm.mp3", Music.class);
        assetManager.load("sounds/click.mp3", Sound.class);
        assetManager.load("buttons/Start_bttn.png", Texture.class);
        assetManager.load("buttons/Startpress_bttn.png", Texture.class);
        assetManager.load("buttons/Setting_bttn.png", Texture.class);
        assetManager.load("buttons/Settingpress_bttn.png", Texture.class);
        assetManager.load("buttons/Exit_bttn.png", Texture.class);
        assetManager.load("buttons/Exitpress_bttn.png", Texture.class);
        assetManager.load("buttons/Arrow.png", Texture.class);
        assetManager.load("buttons/Arrow_press.png", Texture.class);
        assetManager.load("buttons/setting.png", Texture.class);
        assetManager.load("setting/BackgroundSetting.png", Texture.class);   
        assetManager.load("setting/Save_bttn.png", Texture.class);
        assetManager.load("setting/Savepress_bttn.png", Texture.class);
        assetManager.load("setting/Exit_bttn.png", Texture.class);
        assetManager.load("setting/Exitpress_bttn.png", Texture.class);
        assetManager.load("Complete/Background.png", Texture.class);
        assetManager.load("Complete/Next_bttn.png", Texture.class);             
        assetManager.load("Complete/Nextpress_bttn.png", Texture.class);
        assetManager.load("LevelSel/Bill1.png", Texture.class);
        assetManager.load("LevelSel/Bill1_complete.png", Texture.class);
        assetManager.load("LevelSel/Bill2.png", Texture.class);
        assetManager.load("LevelSel/Bill2_complete.png", Texture.class);
        assetManager.load("LevelSel/Bill3.png", Texture.class);
        assetManager.load("LevelSel/Bill3_complete.png", Texture.class);
        assetManager.load("LevelSel/Bill4.png", Texture.class);
        assetManager.load("LevelSel/Bill4_complete.png", Texture.class);
        assetManager.finishLoading();

        setScreen(new MenuScreen(this)); // Pass the game instance to MenuScreen
    }

    /**
     * Called by the game loop from the application every time rendering should be performed.
     * Delegates the render call to the current active screen.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Called when the application is destroyed.
     * Disposes of the current screen and all loaded assets to prevent memory leaks.
     */
    @Override
    public void dispose() {
        if (screen != null) screen.dispose(); // Dispose of current active screen
        super.dispose();
        assetManager.dispose(); // CRITICAL: Dispose of assets -> prevents memory leaks
    }
}
