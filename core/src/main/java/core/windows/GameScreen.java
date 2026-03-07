package core.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import core.mechanics.Grid;
import core.mechanics.PathPuzzleGame;
import core.mechanics.Tile;

public class GameScreen extends ScreenAdapter {
    private static final int TILE_SIZE = 100;
    private final PathPuzzleGame game;
    private Grid grid;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private final float gridOffsetX;
    private final float gridOffsetY;

    public GameScreen(PathPuzzleGame game) {
        this.game = game;

        // 1. Create and fill the grid
        grid = new Grid(4, 4);
        grid.randomInitTile();

        // 2. Set up camera (false = Y go upward)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 3. Center the grid to the screen
        gridOffsetX = (Gdx.graphics.getWidth() - TILE_SIZE * 4) / 2f;
        gridOffsetY = (Gdx.graphics.getHeight() - TILE_SIZE * 4) / 2f;

        // 4. Create a ShapeRenderer for drawing shapes
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        // 5. Register click input
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // LibGDX screenY=0 is top, but camera Y=0 is bottom -> flip it
                float worldY = Gdx.graphics.getHeight() - screenY;
                float worldX = screenX;

                // Convert pixel position to grid position
                int tileX = (int) ((worldX - gridOffsetX) / TILE_SIZE);
                int tileY = (int) ((worldY - gridOffsetY) / TILE_SIZE);

                // Check bounds before accessing the array
                if ((tileX >= 0 && tileX < grid.getCols()) &&
                    tileY >= 0 && tileY < grid.getRows()) {
                    grid.getTiles()[tileY][tileX].rotateClockwise();
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        // 1. Clear the screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Draw filled tile backgrounds
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int y=0; y<grid.getRows(); y++) {
            for (int x=0; x<grid.getCols(); x++) {
                float px = gridOffsetX + x * TILE_SIZE;
                float py = gridOffsetY + y * TILE_SIZE;
                shapeRenderer.setColor(Color.DARK_GRAY);
                shapeRenderer.rect(px, py, TILE_SIZE, TILE_SIZE);
            }
        }
        shapeRenderer.end();

        // Draw path lines
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int y=0; y<grid.getRows(); y++) {
            for (int x=0; x<grid.getCols(); x++) {
                float px = gridOffsetX + x * TILE_SIZE;
                float py = gridOffsetY + y * TILE_SIZE;
                drawTilePaths(grid.getTiles()[y][x], px, py);
            }
        }
        shapeRenderer.end();
    }

    // Draw the path line for one tile based on its type + rotation
    private void drawTilePaths(Tile tile, float px, float py) {
        shapeRenderer.setColor(Color.YELLOW);
        float cx = px + TILE_SIZE / 2f;     // center X of the tile
        float cy = py + TILE_SIZE / 2f;     // center Y of the tile
        float hw = 6;                       // half-width of the tile

        // N = top, E = right, S = bottom, W = left
        boolean n = hasConnection(tile, 0);
        boolean e = hasConnection(tile, 1);
        boolean s = hasConnection(tile, 2);
        boolean w = hasConnection(tile, 3);

        if (n) shapeRenderer.rect(cx - hw, cy,      hw * 2, TILE_SIZE / 2f);
        if (s) shapeRenderer.rect(cx - hw, py,      hw * 2, TILE_SIZE / 2f);
        if (e) shapeRenderer.rect(cx,      cy - hw, TILE_SIZE / 2f, hw * 2);
        if (w) shapeRenderer.rect(px,      cy - hw, TILE_SIZE / 2f, hw * 2);
    }

    // Check whether a tile has a connection to the given direction
    // direction: 0 = N, 1 = E, 2 = S, 3 = W
    private boolean hasConnection(Tile tile, int direction) {
        // Base connection at 0 degrees rotation for each tile type
        boolean[] base;
        switch (tile.getType()) {
            case STRAIGHT   -> base = new boolean[]{true, false, true, false};
            case L_TURN     -> base = new boolean[]{true, true, false, false};
            case T_JUNCTION -> base = new boolean[]{true, true, false, true};
            case CROSS      -> base = new boolean[]{true, true, true, true};
            default         -> base = new boolean[]{false, false, false, false};
        }
        // Rotate direction index backwards by the tile's rotation steps'
        int steps = tile.getRotation() / 90;
        return base[(direction - steps + 4) % 4];
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
