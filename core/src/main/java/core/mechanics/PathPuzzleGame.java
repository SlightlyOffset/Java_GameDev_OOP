package core.mechanics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import core.windows.MenuScreen;

public class PathPuzzleGame extends Game {
    public AssetManager assetManager;
    public float sfxVolume = 1f;
    public float musicVolume = 1f;
    @Override
    public void create() {
        assetManager = new AssetManager();

        // Preload assets
        assetManager.load("logo.png", Texture.class);
        assetManager.load("background.png", Texture.class);
        assetManager.load("menu_bgm.mp3", Music.class);
        assetManager.load("click.mp3", Sound.class);
        assetManager.load("buttons/Start_bttn.png", Texture.class);
        assetManager.load("buttons/Startpress_bttn.png", Texture.class);
        assetManager.load("buttons/Setting_bttn.png", Texture.class);
        assetManager.load("buttons/Settingpress_bttn.png", Texture.class);
        assetManager.load("buttons/Exit_bttn.png", Texture.class);
        assetManager.load("buttons/Exitpress_bttn.png", Texture.class);
        assetManager.load("setting/BackgroundSetting.png", Texture.class);   
        assetManager.load("setting/Save_bttn.png", Texture.class);
        assetManager.load("setting/Savepress_bttn.png", Texture.class);
        assetManager.load("setting/Exit_bttn.png", Texture.class);
        assetManager.load("setting/Exitpress_bttn.png", Texture.class);
        assetManager.finishLoading();

        setScreen(new MenuScreen(this)); // Pass the game instance to MenuScreen
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (screen != null) screen.dispose(); // Dispose of current active screen
        super.dispose();
        assetManager.dispose(); // CRITICAL: Dispose of assets -> prevents memory leaks
    }
}
