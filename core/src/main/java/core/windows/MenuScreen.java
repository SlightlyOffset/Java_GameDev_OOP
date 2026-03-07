package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.TextureBinder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

    public MenuScreen(PathPuzzleGame game) {
        this.game = game;
        this.assetManager = game.assetManager;
    }

    @Override
    public void show() {
        viewport = new FitViewport(800, 480); // Maintain aspect ratio
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage); // Let the stage handle input events (click events)

        table = new Table();
        table.setFillParent(true); // Make the table the size of the viewport
        table.right();  // Align the table to the right side per spec
        stage.addActor(table);

        // Add Logo and Buttons to the table
        setupUI();
    }

    private void setupUI() {
        // Add Logo (image)
        // Check if loaded before using to avoid crash in tests where it might not be mocked fully
        if (assetManager.isLoaded("Logo.png", Texture.class)) {
            Image logo = new Image(assetManager.get("Logo.png", Texture.class));
            table.add(logo).padBottom(50).row(); // Move to next row after adding logo
        }

        // Create Buttons - Commented out for now to prevent crash until skins are loaded
        // TextButton startButton = new TextButton("Start", new TextButton.TextButtonStyle());
        // TextButton exitButton = new TextButton("Exit", new TextButton.TextButtonStyle());

        // Add listeners for interaction
        /*
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); // Start the game -> Transition to GameScreen
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the game
            }
        });

        table.add(startButton).fillX().uniformX().pad(10).row();
        table.add(exitButton).fillX().uniformX().pad(10);
        */
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with black color
        
        // Draw background if we have one
        if (assetManager.isLoaded("background.png", Texture.class)) {
            stage.getBatch().begin();
            stage.getBatch().draw(assetManager.get("background.png", Texture.class), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            stage.getBatch().end();
        }

        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
