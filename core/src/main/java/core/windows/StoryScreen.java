package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import core.mechanics.PathPuzzleGame;

public class StoryScreen implements Screen {

    private final PathPuzzleGame game;
    private final AssetManager assetManager;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Music music;
    private Sound clickSound;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Animation<TextureRegion> animation;
    private Array<Texture> textures = new Array<>();

    private float stateTime = 0;
    private float totalDuration = 0;

    public StoryScreen(PathPuzzleGame game) {
        this.game = game;
        this.assetManager = game.assetManager;
        this.batch = new SpriteBatch(); // Initialize batch here
    }

    private void initSkin() {
        skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));
        pixmap.dispose();
    }

    @Override
    public void show() {
        viewport = new FitViewport(1920, 1080);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        initSkin();
        setupUI();

        // 🎵 Music logic
        if (assetManager.isLoaded("sounds/menu_bgm.mp3", Music.class)) {
            music = assetManager.get("sounds/menu_bgm.mp3", Music.class);
            music.setLooping(true);
            if (!music.isPlaying()) music.play();
        }

        // 🎬 Animation Loading
        textures.clear(); 
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i <= 999; i++) {
            String path = "into/frame_" + String.format("%03d", i) + ".png";
            stateTime = 0;
            if (!Gdx.files.internal(path).exists()) break;

            Texture tex = new Texture(Gdx.files.internal(path));
            textures.add(tex); 
            frames.add(new TextureRegion(tex));
        }

        if (frames.size > 0) {
            animation = new Animation<>(1f / 7f, frames);
        }
    }

    private void setupUI() {
        if (assetManager.isLoaded("sounds/click.mp3", Sound.class)) {
            clickSound = assetManager.get("sounds/click.mp3", Sound.class);
        }

        // Make sure these are loaded in your AssetManager before this screen shows!
        Texture backTex = assetManager.get("buttons/Arrow.png", Texture.class);
        Texture backPressTex = assetManager.get("buttons/Arrow_press.png", Texture.class);
        Texture nextTex = assetManager.get("buttons/Arrow_left.png", Texture.class);
        Texture nextPressTex = assetManager.get("buttons/Arrow_left_press.png", Texture.class);

        ImageButton backBtn = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(backTex)),
                new TextureRegionDrawable(new TextureRegion(backPressTex)));

        ImageButton nextBtn = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(nextTex)),
                new TextureRegionDrawable(new TextureRegion(nextPressTex)));

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);
                game.setScreen(new MenuScreen(game));
            }
        });

        nextBtn.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (clickSound != null) clickSound.play(game.sfxVolume);
            game.setScreen(new LevelSelectionScreen(game));
        }
        });

        backBtn.setSize(143, 100);
        nextBtn.setSize(143, 100);
        backBtn.setPosition(50, 950);
        nextBtn.setPosition(1727, 50);

        stage.addActor(backBtn);
        stage.addActor(nextBtn);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();

        stateTime += delta;

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        if (animation != null) {
            TextureRegion frame = animation.getKeyFrame(stateTime, false);
            batch.draw(frame, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        }
        batch.end();

        stage.act(delta);
        stage.draw();

        if (animation != null && animation.isAnimationFinished(stateTime)) {
            game.setScreen(new LevelSelectionScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        // Dispose textures in the array
        for (Texture tex : textures) {
            tex.dispose();
        }
        textures.clear();

        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
        if (batch != null) batch.dispose();
        if (atlas != null) atlas.dispose();
    }
}