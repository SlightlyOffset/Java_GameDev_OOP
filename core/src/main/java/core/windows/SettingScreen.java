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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import core.mechanics.PathPuzzleGame;


public class SettingScreen implements Screen {
    private final AssetManager assetManager;
    private final PathPuzzleGame game;
    private Stage stage;
    private Table table;
    private Viewport viewport;
    private Skin skin;
    private Music music;
    private Sound clickSound;
    private Label sfxLabel, musicLabel;
    private Slider sfxSlider, musicSlider;
    private float sfxVolume = 1f;
    public SettingScreen(PathPuzzleGame game) {
        this.game = game;
        this.assetManager = game.assetManager;
    }

private void initSkin() {
    if (Gdx.graphics == null) return;

    skin = new Skin();

    // ===== FONT =====
    BitmapFont font = new BitmapFont();
    skin.add("default", font);

    // ===== PIXMAP =====
    Pixmap pixmap = new Pixmap(200, 80, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.WHITE);
    pixmap.fill();

    Texture texture = new Texture(pixmap);
    skin.add("background", texture);
    pixmap.dispose();

    // ===== LABEL STYLE =====
    Label.LabelStyle labelStyle = new Label.LabelStyle();
    labelStyle.font = font;
    labelStyle.fontColor = Color.WHITE;
    skin.add("default", labelStyle);

    // ===== SLIDER STYLE =====
    Slider.SliderStyle sliderStyle = new Slider.SliderStyle();

    sliderStyle.background = skin.newDrawable("background", Color.DARK_GRAY);
    sliderStyle.knob = skin.newDrawable("background", Color.WHITE);

    skin.add("default-horizontal", sliderStyle);

    // ===== BUTTON STYLE =====
    TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

    buttonStyle.up = skin.newDrawable("background", new Color(0, 0, 0, 0.3f));
    buttonStyle.over = skin.newDrawable("background", new Color(1, 1, 1, 0.2f));
    buttonStyle.down = skin.newDrawable("background", new Color(1, 1, 1, 0.4f));
    buttonStyle.checked = skin.newDrawable("background", Color.BLUE);

    buttonStyle.font = font;

    skin.add("default", buttonStyle);
}
    
    @Override
    public void show() {
        viewport = new FitViewport(1920, 1080); // Use ScreenViewport to prevent stretching
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage); // Let the stage handle input events (click events)
        initSkin(); 

        sfxLabel = new Label("SFX", skin);
        musicLabel = new Label("Music", skin);

        sfxSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicSlider = new Slider(0f, 1f, 0.01f, false, skin);
        sfxVolume = game.sfxVolume;
        float musicVolume = game.musicVolume;
        sfxSlider.setValue(sfxVolume);
        musicSlider.setValue(musicVolume);
        setupUI();

        // Play background music if loaded
        if (assetManager.isLoaded("menu_bgm.mp3", Music.class)) {
            music = assetManager.get("menu_bgm.mp3", Music.class);
            music.setLooping(true);
            music.setVolume(musicSlider.getValue());
            music.play();
        }
    }

    private void setupUI() {
        // Load click sound
        if (assetManager.isLoaded("click.mp3", Sound.class)) {
            clickSound = assetManager.get("click.mp3", Sound.class);
        }
        
        // Create Buttons
        Texture Save = assetManager.get("setting/Save_bttn.png", Texture.class);
        Texture Savepress = assetManager.get("setting/Savepress_bttn.png", Texture.class);
        Texture Exit = assetManager.get("setting/Exit_bttn.png", Texture.class);
        Texture Exitpress = assetManager.get("setting/Exitpress_bttn.png", Texture.class);

        // Create ImageButton styles
        ImageButton.ImageButtonStyle SaveStyle = new ImageButton.ImageButtonStyle();
        SaveStyle.up = new TextureRegionDrawable(new TextureRegion(Save));
        SaveStyle.over = new TextureRegionDrawable(new TextureRegion(Savepress));

        ImageButton.ImageButtonStyle ExitStyle = new ImageButton.ImageButtonStyle();
        ExitStyle.up = new TextureRegionDrawable(new TextureRegion(Exit));
        ExitStyle.over = new TextureRegionDrawable(new TextureRegion(Exitpress));


        ImageButton SaveButton = new ImageButton(SaveStyle);
        ImageButton ExitButton = new ImageButton(ExitStyle);

        //set position on button
        ExitButton.setPosition(1070,100);
        SaveButton.setPosition(350,100);

        //set size on button
        SaveButton.setSize(500, 238);
        ExitButton.setSize(500, 238);


        //add button to stage
        stage.addActor(SaveButton);
        stage.addActor(ExitButton);

        //add sfxLabel position
        sfxLabel.setPosition(400, 700);
        sfxSlider.setPosition(600, 700);
        sfxSlider.setSize(800, 50);

        //add musicLabel position
        musicLabel.setPosition(400, 600);
        musicSlider.setPosition(600, 600);
        musicSlider.setSize(800, 50);

        //add label and slider to stage
        stage.addActor(sfxLabel);
        stage.addActor(sfxSlider);
        stage.addActor(musicLabel);
        stage.addActor(musicSlider);
        // Add listeners for interaction
        SaveButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (clickSound != null) clickSound.play(sfxVolume);

            game.sfxVolume = sfxVolume;
            game.musicVolume = musicSlider.getValue();

            game.setScreen(new MenuScreen(game));
        }
        });

        sfxSlider.addListener(new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            sfxVolume = sfxSlider.getValue();
        }
        });

        musicSlider.addListener(new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            if (music != null) {
                music.setVolume(musicSlider.getValue());
            }
        }
        });

        ExitButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (clickSound != null) clickSound.play(sfxVolume);

            // ❗คืนค่าเพลงกลับเป็นของเดิม
            if (music != null) {
                music.setVolume(game.musicVolume);
            }

            game.setScreen(new MenuScreen(game));
        }
    });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with black color
        
        viewport.apply();
        
        // Draw background if we have one
        stage.getBatch().begin();
        // load background texture for settings screen
        if (assetManager.isLoaded("setting/BackgroundSetting.png", Texture.class)) {
            stage.getBatch().draw(assetManager.get("setting/BackgroundSetting.png", Texture.class), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
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
