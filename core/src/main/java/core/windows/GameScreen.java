package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import core.mechanics.Grid;
import core.mechanics.LevelLoader;
import core.mechanics.PathPuzzleGame;
import core.mechanics.PlaytimeTimer; //Thread time
import core.rendering.GdxRenderer;
import core.rendering.IRenderer;
import core.rendering.WorldRenderer;

/**
 * The main screen for the game, responsible for rendering the puzzle grid and handling user input.
 * It manages the game state, including level loading, rendering, and win conditions.
 */
public class GameScreen extends ScreenAdapter {
    private static final int TILE_SIZE = 95;
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
            gridOffsetX = (Gdx.graphics.getWidth() - TILE_SIZE * grid.getCols()) / 2f;
            gridOffsetY = (Gdx.graphics.getHeight() - TILE_SIZE * grid.getRows()) / 2f;

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
        }

        timer.reset(); //reset time in new level
        timer.resume();

        // 6. Register click input
        Gdx.input.setInputProcessor(new InputAdapter() {
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
    }

    /**
     * Called when the screen should render itself.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if (grid == null || camera == null || shapeRenderer == null) {
            return;
        }

        // 1. Clear the screen using the pluggable renderer
        worldRenderer.clearScreen();

        camera.update();

        // Render Background
        if (backgroundTexture != null) {
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }

        shapeRenderer.setProjectionMatrix(camera.combined);

        // 2. Delegate world rendering to WorldRenderer
        if (renderer instanceof GdxRenderer) {
            ((GdxRenderer) renderer).setShapeRenderer(shapeRenderer);
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        worldRenderer.render(grid, gridOffsetX, gridOffsetY, backgroundTexture != null);
        shapeRenderer.end();

        //for Thread time
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Time: " + timer.getFormattedTime(), 20, Gdx.graphics.getHeight() - 20);
        batch.end();
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

        gridOffsetX = (width - TILE_SIZE * grid.getCols()) / 2f;
        gridOffsetY = (height - TILE_SIZE * grid.getRows()) / 2f;
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
            PathPuzzleGame.unlockedLevels[currentLevelIndex] = true;
            if (currentLevelIndex + 1 < PathPuzzleGame.LEVELS.length) {
                        PathPuzzleGame.unlockedLevels[currentLevelIndex + 1] = true;
                    }
            game.setScreen(new CompleteScreen(game, currentLevelIndex));
            dispose();
        }
    }
}