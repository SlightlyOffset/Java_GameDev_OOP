package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import core.mechanics.PathPuzzleGame;

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
        Pixmap pixmap = new Pixmap(1000, 300, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0,0,0,0.01f)); //no color, fully transparent
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        // Create a button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up   = skin.newDrawable("background", new Color(0,0,0,0.01f));
        buttonStyle.over = skin.newDrawable("background", new Color(1,1,1,0.15f));
        buttonStyle.checked = skin.newDrawable("background", com.badlogic.gdx.graphics.Color.BLUE);
        buttonStyle.down = skin.newDrawable("background", new Color(1,1,1,0.3f));
        buttonStyle.font = skin.getFont("default");
        skin.add("default", buttonStyle);
        
        pixmap.dispose();
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
        // have logo in the background
        //if (assetManager.isLoaded("logo.png", Texture.class)) {
        //    Image logo = new Image(assetManager.get("logo.png", Texture.class));
        //    table.add(logo).padBottom(50).row(); // Move to next row after adding logo
        //}

        // Load click sound
        if (assetManager.isLoaded("click.mp3", Sound.class)) {
            clickSound = assetManager.get("click.mp3", Sound.class);
        }

        // Create Buttons
        TextButton startButton = new TextButton("Start", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        //set position on button
        startButton.setPosition(823,550);
        optionsButton.setPosition(821,288);
        exitButton.setPosition(1292,285);

        //set size on button
        startButton.setSize(910, 235);
        optionsButton.setSize(442, 238);
        exitButton.setSize(438, 242);


        //add button to stage
        stage.addActor(startButton);
        stage.addActor(optionsButton);
        stage.addActor(exitButton);

        // Add listeners for interaction
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play();
                game.setScreen(new GameScreen(game)); // Start the game -> Transition to GameScreen
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

        // change to set position on button
        //table.add(startButton).fillX().uniformX().pad(10).row();
        //table.add(optionsButton).fillX().uniformX().pad(10).row();
        //table.add(exitButton).fillX().uniformX().pad(10);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with black colorฃ
        
        viewport.apply();
        
        // Draw background if we have one
        stage.getBatch().begin();
        if (assetManager.isLoaded("background.png", Texture.class)) {
            stage.getBatch().draw(assetManager.get("background.png", Texture.class), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
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
