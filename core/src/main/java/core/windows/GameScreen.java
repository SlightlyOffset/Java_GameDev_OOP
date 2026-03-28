package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import core.mechanics.Grid;
import core.mechanics.LevelLoader;
import core.mechanics.PathPuzzleGame; //Thread time
import core.mechanics.PlaytimeTimer;
import core.rendering.GdxRenderer;
import core.rendering.IRenderer;
import core.rendering.WorldRenderer;

/**
 * The main screen for the game, responsible for rendering the puzzle grid and handling user input.
 * It manages the game state, including level loading, rendering, and win conditions.
 */
public class GameScreen extends ScreenAdapter {
    private static final int TILE_SIZE = 60;
    private final PathPuzzleGame game;
    private Grid grid;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private float gridOffsetX;
    private float gridOffsetY;
    private final int currentLevelIndex;

    // Pluggable Rendering components
    private final IRenderer renderer;
    private final WorldRenderer worldRenderer;

    // Background
    private Texture backgroundTexture;

    //Thread time
    private PlaytimeTimer timer;
    private SpriteBatch batch;
    private BitmapFont font;

    // UI components
    private Viewport viewport;
    private Stage stage;
    private AssetManager assetManager;
    private com.badlogic.gdx.audio.Sound clickSound;

    /**
     * Constructs a new GameScreen with a default grid.
     * @param game The main game instance.
     */
    public GameScreen(PathPuzzleGame game) {
        this(game, null, 0);
    }

    /**
     * Constructs a new GameScreen by loading a level from a specified path.
     * @param game The main game instance.
     * @param levelPath The file path to the level JSON.
     */
    public GameScreen(PathPuzzleGame game, String levelPath) {
        this(game, levelPath, 0);
    }

    /**
     * Constructs a new GameScreen, loading a specific level and tracking its index.
     * @param game The main game instance.
     * @param levelPath The file path to the level JSON.
     * @param levelIndex The index of the current level in the progression.
     */
    public GameScreen(PathPuzzleGame game, String levelPath, int levelIndex) {
        this.game = game;
        this.currentLevelIndex = levelIndex;
        this.assetManager = game.assetManager;

        viewport = new FitViewport(1920, 1080);
        stage = new Stage(viewport);

        initUI();

        // 1. Create and fill the grid
        if (levelPath != null) {
            grid = LevelLoader.loadLevel(levelPath);
            if (grid.getBackgroundImage() != null && Gdx.files != null) {
                backgroundTexture = new Texture(Gdx.files.internal(grid.getBackgroundImage()));
            }
        }
        else {
            grid = new Grid(4, 4);
            grid.randomInitTile();
        }

        // 2. Initialize Pluggable Renderer
        this.renderer = new GdxRenderer();
        worldRenderer = new WorldRenderer(renderer, TILE_SIZE);

        // 3. Set up camera (false = Y go upward)
        if (Gdx.graphics != null) {
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            // 4. Center the grid to the screen
            gridOffsetX = ((Gdx.graphics.getWidth() - TILE_SIZE * grid.getCols()) / 2f);
            gridOffsetY = ((Gdx.graphics.getHeight() - TILE_SIZE * grid.getRows()) / 2f);

            // 5. Create a ShapeRenderer for drawing shapes
            shapeRenderer = new ShapeRenderer();
        }
        //for thread time
        batch = new SpriteBatch();
        font = new BitmapFont();
    }
    
    /**
     * Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}.
     * Sets up the input processor and starts the level timer.
     */
    @Override
    public void show() {
        //for thread time
        if (timer == null) {
            timer = new PlaytimeTimer();
            timer.start();
            timer.resume();
        }

        timer.reset(); //reset time in new level
        com.badlogic.gdx.InputMultiplexer multiplexer = new com.badlogic.gdx.InputMultiplexer();
        multiplexer.addProcessor(stage);
        // 6. Register click input
        multiplexer.addProcessor(new InputAdapter() {
            @Override
                public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (grid.isSolved()) {
                    int nextIndex = currentLevelIndex + 1;
                    if (nextIndex < PathPuzzleGame.LEVELS.length) {
                        game.setScreen(new GameScreen(game, PathPuzzleGame.LEVEL_PATH + PathPuzzleGame.LEVELS[nextIndex], nextIndex));
                    }
                    else {
                        game.setScreen(new MenuScreen(game));
                    }
                    dispose();
                    return true;
                
                }
                float worldX = screenX;
                float worldY = Gdx.graphics.getHeight() - screenY;
            
                float gridWidth  = grid.getCols() * TILE_SIZE;
                float gridHeight = grid.getRows() * TILE_SIZE;
            
                if (worldX < gridOffsetX || worldX >= gridOffsetX + gridWidth ||
                    worldY < gridOffsetY || worldY >= gridOffsetY + gridHeight) {
                    return true;
                }
            
                int tileX = (int) ((worldX - gridOffsetX) / TILE_SIZE);
                int tileY = (int) ((worldY - gridOffsetY) / TILE_SIZE);
            
                handleTileClick(tileX, tileY);
                return true;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void initUI() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // Create Buttons
        ImageButton.ImageButtonStyle BackStyle = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle SettingStyle = new ImageButton.ImageButtonStyle();
        // Create ImageButton styles
        Texture Back = assetManager.get("buttons/Arrow.png", Texture.class);
        Texture Backpress = assetManager.get("buttons/Arrow_press.png", Texture.class);
        Texture Setting = assetManager.get("buttons/setting.png", Texture.class);

        BackStyle.up = new TextureRegionDrawable(new TextureRegion(Back));
        BackStyle.over = new TextureRegionDrawable(new TextureRegion(Backpress));
        SettingStyle.up = new TextureRegionDrawable(new TextureRegion(Setting));
        ImageButton backBtn = new ImageButton(BackStyle);
        ImageButton SettingBtn = new ImageButton(SettingStyle);

        backBtn.setSize(143, 100);
        backBtn.setPosition(20, 975);

        SettingBtn.setSize(121, 100);
        SettingBtn.setPosition(1800,975);

        // Add listeners for interaction
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });
        SettingBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (clickSound != null) clickSound.play(game.sfxVolume);
                game.setScreen(new SettingScreen(game, GameScreen.this));
                
            }
        });
        stage.addActor(backBtn);
        stage.addActor(SettingBtn);

    }

    /**
     * Called when the screen should render itself.
     * @param delta The time in seconds since the last render.
     */

    @Override
    public void render(float delta) {
        viewport.apply();
        if (grid == null || camera == null) return;


        worldRenderer.clearScreen();
        camera.update();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        

        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }


        worldRenderer.render(grid, gridOffsetX, gridOffsetY, batch, assetManager);
        

        font.getData().setScale(2.0f);
        font.draw(batch, "Time: " + timer.getFormattedTime(), 450, Gdx.graphics.getHeight() - 30);
        
        batch.end();


        stage.act(delta);
        stage.draw();
}

    /**
     * Called when the application is resized.
     * @param width The new width of the screen in pixels.
     * @param height The new height of the screen in pixels.
     * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);

        viewport.update(width, height, true);

        gridOffsetX = ((width - TILE_SIZE * grid.getCols()) / 2f)-60f;
        gridOffsetY = ((height - TILE_SIZE * grid.getRows()) / 2f) ;
    }

    /**
     * Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}.
     * Disposes of all native resources.
     */
    @Override
    public void dispose() {
        if (Gdx.graphics != null) shapeRenderer.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        //for Thread time
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
        if (timer != null) timer.stop();
    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} is paused.
     * Pauses the timer.
     */
    //for Thread time
    @Override
    public void pause() {
        if (timer != null) timer.pause();
    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} is resumed from a paused state.
     * Resumes the timer.
     */
    //for Thread time
    @Override
    public void resume() {
        if (timer != null) timer.resume();
    }

    /**
     * Handles the logic for when a tile is clicked.
     * It rotates the tile and checks if the puzzle has been solved.
     * @param x The x-coordinate of the clicked tile in the grid.
     * @param y The y-coordinate of the clicked tile in the grid.
     */
    public void handleTileClick(int x, int y) {
        grid.getTiles()[y][x].rotateClockwise();
        
        boolean solved = grid.isPathComplete();
        grid.setSolved(solved);
        if (solved) {
            System.out.println("Level Complete!");
            timer.pause();
            String finalTime = timer.getFormattedTime();
            PathPuzzleGame.unlockedLevels[currentLevelIndex] = true;
            if (currentLevelIndex + 1 < PathPuzzleGame.LEVELS.length) {
                        PathPuzzleGame.unlockedLevels[currentLevelIndex + 1] = true;
                    }
            game.saveProgress();
            game.setScreen(new CompleteScreen(game, currentLevelIndex, finalTime));
            dispose();
        }
    }
}