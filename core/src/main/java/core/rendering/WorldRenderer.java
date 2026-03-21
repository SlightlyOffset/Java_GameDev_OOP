package core.rendering;

import core.mechanics.Grid;
import core.mechanics.Tile;

/**
 * Custom Class that handles the logic of drawing the game world.
 * Depends on the custom IRenderer interface, satisfying OOP Requirement 1.2.
 */
public class WorldRenderer {
    private final IRenderer renderer;
    private final int tileSize;

    /**
     * Constructor using Dependency Injection (Satisfies OOP Requirement 1.2).
     * @param renderer Any implementation of IRenderer (AWT or GDX)
     * @param tileSize Size in pixels for each tile
     */
    public WorldRenderer(IRenderer renderer, int tileSize) {
        this.renderer = renderer;
        this.tileSize = tileSize;
    }

    /**
     * Clears the screen using a default background color.
     */
    public void clearScreen() {
        renderer.clearScreen(0.2f, 0.2f, 0.2f, 1.0f);
    }

    /**
     * Main rendering loop logic. Translates Grid state into draw calls.
     */
    public void render(Grid grid, float offsetX, float offsetY) {
        // 1. Draw tile backgrounds
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
                else if (x == grid.getEndX() && y == grid.getEndY()) {
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

    private void drawTilePaths(Tile tile, float px, float py, boolean isSolved) {
        float cx = px + tileSize / 2f;     // center X of the tile
        float cy = py + tileSize / 2f;     // center Y of the tile
        float hw = 6;                      // half-width of the path line

        String pathColor = isSolved ? "#00FF00": "#FFFF00"; // Yellow path lines (Green color if complete)

        if (tile.hasConnection(Tile.Direction.NORTH))
            renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 0, pathColor);
        if (tile.hasConnection(Tile.Direction.EAST))
            renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 1, pathColor);
        if (tile.hasConnection(Tile.Direction.SOUTH))
            renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 2, pathColor);
        if (tile.hasConnection(Tile.Direction.WEST))
            renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 3, pathColor);
    }
}
