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

/**
 * The LevelSelectionScreen provides a visual interface for users to browse and select game levels.
 * It features a layout of "Order Slips" that correspond to individual puzzle levels, 
 * matching the hand-drawn aesthetic of the game's background.
 */
public class LevelSelectionScreen implements Screen {

    /** Manager for handling textures, sounds, and music assets. */
    private final AssetManager assetManager;
    /** The main game instance used for screen transitions and accessing global level data. */
    private final PathPuzzleGame game;
    /** The Scene2D stage for managing UI actors and input. */
    private final Stage stage;
    /** Viewport ensuring the UI scales correctly to a 1920x1080 virtual resolution. */
    private final Viewport viewport;
    /** Programmatic skin for styling UI components like buttons and fonts. */
    private Skin skin;
    /** Sound effect played upon clicking buttons. */
    private Sound clickSound;

    /**
     * Constructs the Level Selection screen.
     * Initializes the viewport, stage, and UI components immediately to prepare for display.
     * 
     * @param game The main game instance.
     */
    public LevelSelectionScreen(PathPuzzleGame game) {
        this.game = game;
        this.assetManager = game.assetManager;

        viewport = new FitViewport(1920, 1080);
        stage = new Stage(viewport);

        initBasicSkin();
        initUI();
    }

    /**
     * Called when this screen becomes the current screen for a Game.
     * Sets up input processing and resumes background music if available.
     */
    @Override
    public void show() {
        if (assetManager.isLoaded("sounds/click.mp3", Sound.class)) {
            clickSound = assetManager.get("sounds/click.mp3", Sound.class);
        }

        Gdx.input.setInputProcessor(stage);

        if (assetManager.isLoaded("sounds/menu_bgm.mp3", Music.class)) {
            Music music = assetManager.get("sounds/menu_bgm.mp3", Music.class);
            music.setVolume(game.musicVolume);
            if (!music.isPlaying()) music.play();
        }
    }

    /**
     * Creates a fallback UI skin programmatically.
     * This avoids dependency on an external JSON skin file and sets up the 
     * semi-transparent aesthetic needed to see the background drawings through the buttons.
     */
    private void initBasicSkin() {
        skin = new Skin();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(3); 
        skin.add("default", font);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        skin.add("white", whiteTexture);
        pixmap.dispose();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", new Color(0, 0, 0, 0.1f)); 
        textButtonStyle.down = skin.newDrawable("white", new Color(0, 0, 0, 0.3f));
        textButtonStyle.font = skin.getFont("default");
        textButtonStyle.fontColor = Color.BLACK;

        skin.add("default", textButtonStyle);
    }

    /**
     * Constructs the UI layout using Scene2D Tables.
     * Includes a navigation bar with a 'Back' button and a grid of level 'Order' buttons
     * generated dynamically from the {@link PathPuzzleGame#LEVELS} array.
     */
    private void initUI() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // Top bar for Back button
        Table topTable = new Table();
        TextButton backBtn = new TextButton("<-- Back", skin);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });
        topTable.add(backBtn).left().pad(30).width(250).height(100);
        rootTable.add(topTable).expandX().top().left().row();

        // Level Slips
        Table levelTable = new Table();
        for (int i = 0; i < PathPuzzleGame.LEVELS.length; i++) {
            final String levelName = PathPuzzleGame.LEVELS[i];
            final int levelIndex = i;
            final int levelNum = i + 1;

            TextButton btn = new TextButton("Order\n\n" + levelNum, skin);
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (clickSound != null) clickSound.play(game.sfxVolume);
                    Gdx.app.log("LevelSelection", "Loading Level: " + levelName);
                    game.setScreen(new GameScreen(game, PathPuzzleGame.LEVEL_PATH + levelName, levelIndex));
                    dispose();
                }
            });
            levelTable.add(btn).width(230).height(400).pad(70);
        }
        rootTable.add(levelTable).expand().top();
    }

    /**
     * Renders the screen every frame.
     * Clears the buffer to white, draws the background texture if loaded, 
     * and updates/draws the Scene2D stage.
     * 
     * @param delta Time in seconds since the last frame.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        viewport.apply();

        stage.getBatch().begin();
        if (assetManager.isLoaded("images/LevelSelBG.png", Texture.class)) {
            stage.getBatch().draw(assetManager.get("images/LevelSelBG.png", Texture.class), 
                0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        }
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    /**
     * Updates the stage's viewport whenever the screen is resized.
     * 
     * @param width The new window width.
     * @param height The new window height.
     */
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
        // We don't stop music here if we want it to continue to GameScreen
        // But usually Menu and LevelSelect share the same BGM
    }

    /**
     * Releases all resources held by this screen, including the stage and skin.
     * Should be called when the screen is no longer needed to prevent memory leaks.
     */
    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}
