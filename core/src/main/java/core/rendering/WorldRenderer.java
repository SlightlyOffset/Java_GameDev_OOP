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

                renderer.drawRect(px, py, tileSize, tileSize, "000000");
                float boderThickness = 1f;

                String bgColor = "#404040";
                if (x == grid.getStartX() && y == grid.getStartY()) {
                    bgColor = "#1A5276";
                }
                else if (x == grid.getEndX() && y == grid.getEndY()) {
                    bgColor = "#922B21";
                }
                // Draw dark gray background
                renderer.drawRect(px + boderThickness, py + boderThickness, tileSize - (boderThickness * 2), tileSize - (boderThickness * 2), bgColor);
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

        // Direction mapping: 0 = N, 1 = E, 2 = S, 3 = W
        boolean n = hasConnection(tile, 0);
        boolean e = hasConnection(tile, 1);
        boolean s = hasConnection(tile, 2);
        boolean w = hasConnection(tile, 3);

        String pathColor = isSolved ? "#00FF00": "#FFFF00"; // Yellow path lines (Green color if complete)

        if (n) renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 0, pathColor);
        if (e) renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 1, pathColor);
        if (s) renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 2, pathColor);
        if (w) renderer.drawPathLine(cx, cy, hw, tileSize / 2f, 3, pathColor);
    }

    private boolean hasConnection(Tile tile, int direction) {
        boolean[] base;
        switch (tile.getType()) {
            case STRAIGHT   -> base = new boolean[]{true, false, true, false};
            case L_TURN     -> base = new boolean[]{true, true, false, false};
            case T_JUNCTION -> base = new boolean[]{true, true, false, true};
            case CROSS      -> base = new boolean[]{true, true, true, true};
            default         -> base = new boolean[]{false, false, false, false};
        }
        int steps = tile.getRotation() / 90;
        return base[(direction - steps + 4) % 4];
    }
}
