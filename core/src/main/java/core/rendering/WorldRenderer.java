package core.rendering;

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
     * Renders the entire game world. It translates the current state of the Grid
     * into a series of draw calls, handling backgrounds, tiles, and paths.
     *
     * @param grid The game grid containing the tiles to render.
     * @param offsetX The horizontal offset at which to start drawing the grid.
     * @param offsetY The vertical offset at which to start drawing the grid.
     */
    public void render(Grid grid, float offsetX, float offsetY) {
        // 1. Draw tile backgrounds
        int resolvedEndX = grid.getEndX();
        int resolvedEndY = grid.getEndY();

        for (int y = 0; y < grid.getRows(); y++) {
            for (int x = 0; x < grid.getCols(); x++) {
                float px = offsetX + x * tileSize;
                float py = offsetY + y * tileSize;

                renderer.drawRect(px, py, tileSize, tileSize, "#000000");
                float borderThickness = 1f;

                String bgColor = "#404040";
                if (x == grid.getStartX() && y == grid.getStartY()) {
                    bgColor = "#1A5276";
                }
                else if (x == resolvedEndX && y == resolvedEndY) {
                    bgColor = "#922B21";
                }
                // Draw dark gray background
                renderer.drawRect(px + borderThickness, py + borderThickness, tileSize - (borderThickness * 2), tileSize - (borderThickness * 2), bgColor);
            }
        }

        // 2. Draw path lines
        for (int y = 0; y < grid.getRows(); y++) {
            for (int x = 0; x < grid.getCols(); x++) {
                float px = offsetX + x * tileSize;
                float py = offsetY + y * tileSize;
                drawTilePaths(grid.getTiles()[y][x], px, py, grid.isSolved());
            }
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
    private void drawTilePaths(Tile tile, float px, float py, boolean isSolved) {
        float cx = px + tileSize / 2f;     // center X of the tile
        float cy = py + tileSize / 2f;     // center Y of the tile
        float hw = 2;                      // half-width of the path line

        String pathColor = isSolved ? "#00FF00": "#FFFF00"; // Yellow path lines (Green color if complete)

        boolean[] connections = tile.getType().getConnections(tile.getRotation());
        if (connections[Tile.Direction.NORTH.ordinal()])
            renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 0, pathColor);
        if (connections[Tile.Direction.EAST.ordinal()])
            renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 1, pathColor);
        if (connections[Tile.Direction.SOUTH.ordinal()])
            renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 2, pathColor);
        if (connections[Tile.Direction.WEST.ordinal()])
            renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 3, pathColor);
    }
}
