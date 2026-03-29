package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import core.mechanics.PathPuzzleGame;

/**
 * Presents the level-select hub that reflects unlock and completion state using the
 * textures stored under {@code assets/LevelSel}. The shared {@link PathPuzzleGame}
 * reference supplies the {@link AssetManager}, music preferences, and canonical
 * level metadata used to launch {@link GameScreen} instances.
 */
public class LevelSelectionScreen implements Screen {

    private final AssetManager assetManager;
    private final PathPuzzleGame game;
    private final Stage stage;
    private final Viewport viewport;
    private Sound clickSound;

    public LevelSelectionScreen(PathPuzzleGame game) {
        this.game = game;
        this.assetManager = game.assetManager;
        this.viewport = new FitViewport(1920, 1080);
        this.stage = new Stage(viewport);
        initUI();
    }

    /**
     * Called when the screen becomes active; wires input handling and ensures
     * menu background music plays using the persisted volume.
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
     * Builds the static UI: back/settings controls plus the four bill buttons
     * whose textures respond to unlocked/completed state.
     */
    private void initUI() {
        // --- ส่วน Setting & Back ---
        Texture backTex = assetManager.get("buttons/Arrow.png", Texture.class);
        Texture backPressTex = assetManager.get("buttons/Arrow_press.png", Texture.class);
        Texture settingTex = assetManager.get("buttons/setting.png", Texture.class);

        ImageButton backBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTex)), 
                                             new TextureRegionDrawable(new TextureRegion(backPressTex)));
        ImageButton settingBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingTex)));

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });

        settingBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);
                game.setScreen(new SettingScreen(game, LevelSelectionScreen.this));
            }
        });

        backBtn.setSize(143, 100);
        backBtn.setPosition(20, 975);
        settingBtn.setSize(121, 100);
        settingBtn.setPosition(1800, 975);

        stage.addActor(backBtn);
        stage.addActor(settingBtn);

        float startX = 200;
        float spacing = 400;


        String[] billPaths = {"LevelSel/Bill1.png", "LevelSel/Bill2.png", "LevelSel/Bill3.png", "LevelSel/Bill4.png"};
        String[] completePaths = {"LevelSel/Bill1_complete.png", "LevelSel/Bill2_complete.png", "LevelSel/Bill3_complete.png", "LevelSel/Bill4_complete.png"};

        for (int i = 0; i < 4; i++) {
            final int levelIndex = i;
            Texture normalTex = assetManager.get(billPaths[i], Texture.class);
            Texture completeTex = assetManager.get(completePaths[i], Texture.class);
            
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();


    if (PathPuzzleGame.completedLevels[i]) {
        style.up = new TextureRegionDrawable(new TextureRegion(completeTex));
    } else {
        
        style.up = new TextureRegionDrawable(new TextureRegion(normalTex));
    }
    style.down = new TextureRegionDrawable(new TextureRegion(completeTex));

    ImageButton billBtn = new ImageButton(style);
    billBtn.setSize(314, 474);
            billBtn.setPosition(startX + (i * spacing), 465); 

    
    if (!PathPuzzleGame.unlockedLevels[i]) {
        billBtn.setDisabled(true);
        billBtn.setColor(0.3f, 0.3f, 0.3f, 1.0f); 
    } else {
        billBtn.setColor(Color.WHITE); // ด่าน 1 จะเป็นสีขาวปกติ
        billBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);
                game.setScreen(new GameScreen(game, PathPuzzleGame.LEVEL_PATH + PathPuzzleGame.LEVELS[levelIndex], levelIndex));
                dispose();
            }
                });
            }
            stage.addActor(billBtn);
        }
    }

    /**
     * Draws the background, advances the stage, and renders all actors.
     * @param delta time elapsed since the previous frame.
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
     * Keeps the fixed-art viewport centered when the window changes size.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /** Screen pause hook (unused but required by {@link Screen}). */
    @Override
    public void pause() {}

    /** Screen resume hook (unused but required by {@link Screen}). */
    @Override
    public void resume() {}

    /** Screen hide hook (unused but required by {@link Screen}). */
    @Override
    public void hide() {}

    /**
     * Disposes the stage and skin to release GPU resources when the screen ends.
     */
    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }
}