package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import core.mechanics.PathPuzzleGame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Pixmap;

import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.audio.Sound;

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
        initSkin();
    }

    private void initSkin() {
        // Skip skin initialization in tests if Gdx.graphics is not available
        if (Gdx.graphics == null) return;
        
        skin = new Skin();
        // Create a default font
        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        // Create a basic texture for button background
        Pixmap pixmap = new Pixmap(100, 30, Pixmap.Format.RGBA8888);
        pixmap.setColor(com.badlogic.gdx.graphics.Color.GRAY);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        // Create a button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("background", com.badlogic.gdx.graphics.Color.GRAY);
        buttonStyle.down = skin.newDrawable("background", com.badlogic.gdx.graphics.Color.DARK_GRAY);
        buttonStyle.checked = skin.newDrawable("background", com.badlogic.gdx.graphics.Color.BLUE);
        buttonStyle.over = skin.newDrawable("background", com.badlogic.gdx.graphics.Color.LIGHT_GRAY);
        buttonStyle.font = skin.getFont("default");
        skin.add("default", buttonStyle);
        
        pixmap.dispose();
    }

    @Override
    public void show() {
        viewport = new ScreenViewport(); // Use ScreenViewport to prevent stretching
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage); // Let the stage handle input events (click events)

        table = new Table();
        table.setFillParent(true); // Make the table the size of the viewport
        table.right();  // Align the table to the right side per spec
        stage.addActor(table);

        // Add Logo and Buttons to the table
        setupUI();

        // Play background music if loaded
        if (assetManager.isLoaded("menu_bgm.mp3", Music.class)) {
            music = assetManager.get("menu_bgm.mp3", Music.class);
            music.setLooping(true);
            music.play();
        }
    }

    private void setupUI() {
        // Add Logo (image)
        if (assetManager.isLoaded("logo.png", Texture.class)) {
            Image logo = new Image(assetManager.get("logo.png", Texture.class));
            table.add(logo).padBottom(50).row(); // Move to next row after adding logo
        }

        // Load click sound
        if (assetManager.isLoaded("click.mp3", Sound.class)) {
            clickSound = assetManager.get("click.mp3", Sound.class);
        }

        // Create Buttons
        TextButton startButton = new TextButton("Start", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Add listeners for interaction
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play();
                game.setScreen(new GameScreen(game, "levels/level_1.json")); // Start the game -> Transition to GameScreen
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play();
                Gdx.app.log("MenuScreen", "Options button clicked - placeholder");
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play();
                Gdx.app.exit(); // Exit the game
            }
        });

        // Add buttons to the table
        table.add(startButton).fillX().uniformX().pad(10).row();
        table.add(optionsButton).fillX().uniformX().pad(10).row();
        table.add(exitButton).fillX().uniformX().pad(10);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with black color
        
        // Draw background if we have one
        if (assetManager.isLoaded("background.png", Texture.class)) {
            stage.getBatch().begin();
            stage.getBatch().draw(assetManager.get("background.png", Texture.class), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.getBatch().end();
        }

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
        if (music != null) {
            music.stop();
        }
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}
