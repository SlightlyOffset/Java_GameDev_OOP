package core.rendering;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import core.mechanics.Grid;
import core.mechanics.Tile;

/**
 * Handles the logic of drawing the game world, including the grid, tiles, and paths.
 * This class is decoupled from the specific rendering technology through the IRenderer interface.
 */
public class WorldRenderer {
    private final IRenderer renderer;
    private final int tileSize;

    /**
     * Constructs a new WorldRenderer.
     * This constructor uses Dependency Injection to accept any implementation of IRenderer,
     * which satisfies OOP requirements for abstraction.
     *
     * @param renderer The rendering implementation (e.g., AwtRenderer, GdxRenderer).
     * @param tileSize The size of each tile in pixels.
     */
    public WorldRenderer(IRenderer renderer, int tileSize) {
        this.renderer = renderer;
        this.tileSize = tileSize;
    }

    /**
     * Clears the screen with a default background color.
     */
    public void clearScreen() {
        renderer.clearScreen(0.2f, 0.2f, 0.2f, 1.0f);
    }
    public void render(Grid grid, float offsetX, float offsetY, SpriteBatch batch, AssetManager assetManager) {
    for (int y = 0; y < grid.getRows(); y++) {
        for (int x = 0; x < grid.getCols(); x++) {
            Tile tile = grid.getTiles()[y][x];
            String texturePath = tile.getType().getTextureName();

            if (texturePath == null || texturePath.isEmpty()) continue;

            if (assetManager.isLoaded(texturePath, Texture.class)) {
                Texture tex = assetManager.get(texturePath, Texture.class);
                
                float px = offsetX + x * tileSize;
                float py = offsetY + y * tileSize;

                if (grid.isSolved()) {
                    batch.setColor(com.badlogic.gdx.graphics.Color.GREEN);
                } else {
                    batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                }

                batch.draw(
                    new TextureRegion(tex),
                    px, py,
                    tileSize / 2f, tileSize / 2f,
                    tileSize, tileSize,
                    1f, 1f,
                    -tile.getRotation()
                );
            }
        }
        batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
    }
}

    /**
     * Draws the path segments for a single tile based on its type and rotation.
     *
     * @param tile The tile to draw.
     * @param px The pixel x-coordinate of the tile's top-left corner.
     * @param py The pixel y-coordinate of the tile's top-left corner.
     * @param isSolved Whether the puzzle is currently in a solved state, which changes the path color.
     */
}