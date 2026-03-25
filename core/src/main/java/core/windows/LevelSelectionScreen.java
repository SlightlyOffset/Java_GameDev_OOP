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

public class LevelSelectionScreen implements Screen {

    private final AssetManager assetManager;
    private final PathPuzzleGame game;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Sound clickSound;

    public LevelSelectionScreen(PathPuzzleGame game) {
        this.game = game;
        this.assetManager = game.assetManager;
    }

    @Override
    public void show() {
        if (assetManager.isLoaded("sounds/click.mp3", Sound.class)) {
            clickSound = assetManager.get("sounds/click.mp3", Sound.class);
        }

        viewport = new FitViewport(1920, 1080);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        initBasicSkin();
        initUI();

        if (assetManager.isLoaded("sounds/menu_bgm.mp3", Music.class)) {
            Music music = assetManager.get("sounds/menu_bgm.mp3", Music.class);
            if (!music.isPlaying()) {
                music.setLooping(true);
                music.play();
            }
        }
    }

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
                clickSound.play();
                game.setScreen(new MenuScreen(game));
            }
        });
        topTable.add(backBtn).left().pad(30).width(250).height(100);
        rootTable.add(topTable).expandX().top().left().row();

        // Level Slips
        Table levelTable = new Table();
        for (int i = 0; i < game.LEVELS.length; i++) {
            final String levelName = game.LEVELS[i];
            final int levelIndex = i;
            final int levelNum = i + 1;

            TextButton btn = new TextButton("Order\n\n" + levelNum, skin);
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clickSound.play();
                    Gdx.app.log("LevelSelection", "Loading Level: " + levelName);
                    game.setScreen(new GameScreen(game, game.LEVEL_PATH + levelName, levelIndex));
                }
            });
            levelTable.add(btn).width(230).height(400).pad(70);
        }
        rootTable.add(levelTable).expand().top();
    }

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

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}