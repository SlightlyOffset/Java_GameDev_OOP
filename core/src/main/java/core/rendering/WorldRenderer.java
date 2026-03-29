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

    /**
     * Renders the game grid by iterating through all tiles and delegating drawing to the pluggable renderer.
     * This method handles tile positioning, rotation, and color tinting based on puzzle state.
     * 
     * <p>The rendering process:
     * <ul>
     *   <li>Iterates through every tile in the grid (row by row, column by column)</li>
     *   <li>Skips tiles with no texture (EMPTY, CROSS types)</li>
     *   <li>Loads textures from the AssetManager if available</li>
     *   <li>Calculates screen position: grid offset + tile index * tile size</li>
     *   <li>Determines color based on puzzle completion state (GREEN if solved, WHITE otherwise)</li>
     *   <li>Delegates the actual drawing to the renderer abstraction via drawTextureRegion()</li>
     * </ul>
     * </p>
     * 
     * <p>Tile rotation is negated when passed to the renderer to account for LibGDX's 
     * counter-clockwise convention (tile rotates clockwise, but LibGDX rotates counter-clockwise).
     * Rotation origin is set to the tile's center (tileSize/2, tileSize/2) to ensure 
     * rotation around the center point.
     * </p>
     * 
     * <p>The renderer abstraction (IRenderer) is injected with the SpriteBatch for GdxRenderer 
     * implementations to use. This ensures all rendering operations are properly encapsulated 
     * and decoupled from the world rendering logic.
     * </p>
     * 
     * @param grid The game grid containing all tiles to render
     * @param offsetX The x-coordinate offset for positioning the grid on screen
     * @param offsetY The y-coordinate offset for positioning the grid on screen
     * @param batch The SpriteBatch for texture rendering (used by GdxRenderer)
     * @param assetManager The AssetManager for loading textures by path
     * 
     * @see IRenderer#drawTextureRegion(Texture, float, float, float, float, float, float, float, float, float, float, float)
     * @see Grid#getRows()
     * @see Grid#getCols()
     * @see Tile#getType()
     * @see com.badlogic.gdx.assets.AssetManager
     */
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

                    // Determine color based on puzzle completion state
                    float colorR, colorG, colorB, colorA = 1.0f;
                    if (grid.isSolved()) {
                        colorR = 0.0f;
                        colorG = 1.0f;
                        colorB = 0.0f;
                    } else {
                        colorR = 1.0f;
                        colorG = 1.0f;
                        colorB = 1.0f;
                    }

                    // Delegate tile drawing to the renderer abstraction
                    // This passes SpriteBatch to GdxRenderer for actual drawing
                    if (renderer instanceof GdxRenderer) {
                        GdxRenderer gdxRenderer = (GdxRenderer) renderer;
                        gdxRenderer.setSpriteBatch(batch);
                    }

                    renderer.drawTextureRegion(
                        tex,
                        px, py,
                        tileSize / 2f, tileSize / 2f,
                        tileSize, tileSize,
                        -tile.getRotation(),
                        colorR, colorG, colorB, colorA
                    );
                }
            }
        }
    }
}