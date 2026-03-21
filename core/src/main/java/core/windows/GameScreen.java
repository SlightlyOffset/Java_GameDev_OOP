package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import core.mechanics.Grid;
import core.mechanics.LevelLoader;
import core.mechanics.PathPuzzleGame;
import core.mechanics.Tile;
import core.rendering.GdxRenderer;
import core.rendering.IRenderer;
import core.rendering.WorldRenderer;

public class GameScreen extends ScreenAdapter {
    private static final int TILE_SIZE = 100;
    private final PathPuzzleGame game;
    private Grid grid;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private float gridOffsetX;
    private float gridOffsetY;

    // Pluggable Rendering components
    private final IRenderer renderer;
    private final WorldRenderer worldRenderer;

    public GameScreen(PathPuzzleGame game) {
        this(game, null);
    }

    public GameScreen(PathPuzzleGame game, String levelPath) {
        this.game = game;

        // 1. Create and fill the grid
        if (levelPath != null) {
            grid = LevelLoader.loadLevel(levelPath);
            if (grid.getEndX() == 0 && grid.getEndY() == 0) {
                grid.setStartAndEnd(0, 0, grid.getCols() - 1, grid.getRows() - 1);
            }
        } else {
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
                    game.setScreen(new GameScreen(game)); 
                    dispose();
                    return true;
                }
                // LibGDX screenY=0 is top, but camera Y=0 is bottom -> flip it
                float worldX = screenX;
                float worldY = Gdx.graphics.getHeight() - screenY;

                float gridWidth = grid.getCols() * TILE_SIZE;
                float gridHeight = grid.getRows() * TILE_SIZE;

                if (worldX < gridOffsetX || worldX >= gridOffsetX + gridWidth ||
                    worldY < gridOffsetY || worldY >= gridOffsetY + gridHeight) {
                    return true;
                }

                // Convert pixel position to grid position
                int tileX = (int) ((worldX - gridOffsetX) / TILE_SIZE);
                int tileY = (int) ((worldY - gridOffsetY) / TILE_SIZE);

                // Check bounds before accessing the array
                if ((tileX >= 0 && tileX < grid.getCols()) &&
                    tileY >= 0 && tileY < grid.getRows()) {

                    boolean isStartTile = (tileX == grid.getStartX() && tileY == grid.getStartY());
                    boolean isEndTile = (tileX == grid.getEndX() && tileY == grid.getEndY());
                    
                    if (!isStartTile && !isEndTile) {
                        grid.getTiles()[tileY][tileX].rotateClockwise();
                    }

                    if (grid.isPathComplete()) {
                        System.out.println("Level Complete!");
                        grid.setSolved(true);
                    }
                    else {
                        grid.setSolved(false);
                    }
                }
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
        if (Gdx.graphics != null) shapeRenderer.dispose();
    }

    public void handleTileClick(int x, int y) {
    Tile tile = grid.getTiles()[y][x];
    tile.rotateClockwise(); //
    
    if (grid.isPathComplete()) {
        System.out.println("Level Complete!"); 
        // แสดง UI หรือเปลี่ยนด่านได้ที่นี่
    }
}
}
