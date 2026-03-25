package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import core.mechanics.Grid;
import core.mechanics.LevelLoader;
import core.mechanics.PathPuzzleGame;
import core.rendering.GdxRenderer;
import core.rendering.IRenderer;
import core.rendering.WorldRenderer;

public class GameScreen extends ScreenAdapter {
    private static final int TILE_SIZE = 40;
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

    public GameScreen(PathPuzzleGame game) {
        this(game, null, 0);
    }

    public GameScreen(PathPuzzleGame game, String levelPath) {
        this(game, levelPath, 0);
    }

    public GameScreen(PathPuzzleGame game, String levelPath, int levelIndex) {
        this.game = game;
        this.currentLevelIndex = levelIndex;

        // 1. Create and fill the grid
        if (levelPath != null) {
            grid = LevelLoader.loadLevel(levelPath);
        }
        else {
            grid = new Grid(4, 4);
            grid.randomInitTile();
        }

        // 2. Initialize Pluggable Renderer
        GdxRenderer gdxRenderer = new GdxRenderer();
        this.renderer = gdxRenderer;
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
    }

    @Override
    public void show() {
        // 6. Register click input
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (grid.isSolved()) {
                    int nextIndex = currentLevelIndex + 1;
                    if (nextIndex < game.LEVELS.length) {
                        game.setScreen(new GameScreen(game, game.LEVEL_PATH + game.LEVELS[nextIndex], nextIndex));
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

    @Override
    public void render(float delta) {
        if (grid == null || camera == null || shapeRenderer == null) {
            return;
        }

        // 1. Clear the screen using the pluggable renderer
        worldRenderer.clearScreen();

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        // 2. Delegate world rendering to WorldRenderer
        if (renderer instanceof GdxRenderer) {
            ((GdxRenderer) renderer).setShapeRenderer(shapeRenderer);
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        worldRenderer.render(grid, gridOffsetX, gridOffsetY);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);

        gridOffsetX = (width - TILE_SIZE * grid.getCols()) / 2f;
        gridOffsetY = (height - TILE_SIZE * grid.getRows()) / 2f;
    }

    @Override
    public void dispose() {
        if (Gdx.graphics != null) {
            shapeRenderer.dispose();
        }
    }

    public void handleTileClick(int x, int y) {
        // boolean isStartTile = (x == grid.getStartX() && y == grid.getStartY());
        // boolean isEndTile = (x == grid.getEndX() && y == grid.getEndY());
        
        // if (!isStartTile && !isEndTile) {
        //     grid.getTiles()[y][x].rotateClockwise();    
        // }
        // grid.setSolved(grid.isPathComplete());
        
        grid.getTiles()[y][x].rotateClockwise();
        
        boolean solved = grid.isPathComplete();
        grid.setSolved(solved);
        if (solved) {
            System.out.println("Level Complete!");
        }
    }
}