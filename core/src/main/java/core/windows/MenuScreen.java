package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import core.mechanics.PathPuzzleGame;

/**
 * Main menu screen for the puzzle game.
 * Displays start, settings, and exit buttons with background music and click sounds.
 */
public class MenuScreen implements Screen {
    private final AssetManager assetManager;
    private final PathPuzzleGame game;
    private Stage stage;
    private Table table;
    private Viewport viewport;
    private Skin skin;
    private Music music;
    private Sound clickSound;

    public MenuScreen(PathPuzzleGame game) {
        this.game = game;
        this.assetManager = game.assetManager;
    }

    @Override
    public void show() {
        viewport = new FitViewport(1920, 1080); // Use ScreenViewport to prevent stretching
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage); // Let the stage handle input events (click events)

        //change to set positon
        //table = new Table();
        //table.setFillParent(true); // Make the table the size of the viewport
        //table.right();  // Align the table to the right side per spec
        //stage.addActor(table);

        // Add Buttons to the stage
        setupUI();

        // Play background music if loaded
        if (assetManager.isLoaded("sounds/menu_bgm.mp3", Music.class)) {
            music = assetManager.get("sounds/menu_bgm.mp3", Music.class);
            music.setLooping(true);
            music.setVolume(game.musicVolume);
            if (!music.isPlaying())
                music.play();
        }
    }

    private void setupUI() {
        // Load click sound
        if (assetManager.isLoaded("sounds/click.mp3", Sound.class)) {
            clickSound = assetManager.get("sounds/click.mp3", Sound.class);
        }

        // Create Buttons
        Texture startUp = assetManager.get("buttons/Start_bttn.png", Texture.class);
        Texture startOver = assetManager.get("buttons/Startpress_bttn.png", Texture.class);
        Texture settingUp = assetManager.get("buttons/Setting_bttn.png", Texture.class);
        Texture settingOver = assetManager.get("buttons/Settingpress_bttn.png", Texture.class);
        Texture exitUp = assetManager.get("buttons/Exit_bttn.png", Texture.class);
        Texture exitOver = assetManager.get("buttons/Exitpress_bttn.png", Texture.class);

        // Create ImageButton styles
        ImageButton.ImageButtonStyle startStyle = new ImageButton.ImageButtonStyle();
        startStyle.up = new TextureRegionDrawable(new TextureRegion(startUp));
        startStyle.over = new TextureRegionDrawable(new TextureRegion(startOver));

        ImageButton.ImageButtonStyle optionsStyle = new ImageButton.ImageButtonStyle();
        optionsStyle.up = new TextureRegionDrawable(new TextureRegion(settingUp));
        optionsStyle.over = new TextureRegionDrawable(new TextureRegion(settingOver));


        ImageButton.ImageButtonStyle exitStyle = new ImageButton.ImageButtonStyle();
        exitStyle.up = new TextureRegionDrawable(new TextureRegion(exitUp));
        exitStyle.over = new TextureRegionDrawable(new TextureRegion(exitOver));

        ImageButton startButton = new ImageButton(startStyle);
        ImageButton optionsButton = new ImageButton(optionsStyle);
        ImageButton exitButton = new ImageButton(exitStyle);

        //set position on button
        startButton.setPosition(823,565);
        optionsButton.setPosition(821,288);
        exitButton.setPosition(1292,285);

        //set size on button
        startButton.setSize(910, 235);
        //optionsButton.setSize(442, 238);
        //exitButton.setSize(438, 242);


        //add button to stage
        stage.addActor(startButton);
        stage.addActor(optionsButton);
        stage.addActor(exitButton);

        // Add listeners for interaction
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);
                game.setScreen(new LevelSelectionScreen(game)); // Start the game -> Transition to LevelSelectionScreen
                dispose();
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);;
                game.setScreen(new SettingScreen(game)); // Transition to SettingScreen
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);;
                Gdx.app.exit(); // Exit the game
            }
        });

        // change to set position on button
        //table.add(startButton).fillX().uniformX().pad(10).row();
        //table.add(optionsButton).fillX().uniformX().pad(10).row();
        //table.add(exitButton).fillX().uniformX().pad(10);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with black color
        
        viewport.apply();
        
        // Draw background if we have one
        stage.getBatch().begin();
        if (assetManager.isLoaded("images/background.png", Texture.class)) {
            stage.getBatch().draw(assetManager.get("images/background.png", Texture.class), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        }
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        // Music continues to next screen if it handles it
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}
