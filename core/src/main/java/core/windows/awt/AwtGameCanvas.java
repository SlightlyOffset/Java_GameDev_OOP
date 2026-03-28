package core.windows.awt;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import core.mechanics.Grid;
import core.rendering.AwtRenderer;
import core.rendering.WorldRenderer;

/**
 * Custom JPanel for rendering the game using AWT.
 * Satisfies OOP Requirement 1.2: Custom Class.
 */
public class AwtGameCanvas extends JPanel {
    private final Grid grid;
    private final AwtRenderer awtRenderer;
    private final WorldRenderer worldRenderer;
    private final int tileSize;
    private float gridOffsetX;
    private float gridOffsetY;

    public AwtGameCanvas(Grid grid, AwtRenderer awtRenderer, WorldRenderer worldRenderer) {
        this.grid = grid;
        this.awtRenderer = awtRenderer;
        this.worldRenderer = worldRenderer;
        this.tileSize = AwtGameWindow.TILE_SIZE; // Use single source of truth from AwtGameWindow
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Satisfies Requirement 1.1: Using Standard Graphics
        Graphics2D g2d = (Graphics2D) g;
        awtRenderer.setGraphics(g2d, getWidth(), getHeight());

        // Clear the screen using the WorldRenderer to match the GDX rendering path
        worldRenderer.clearScreen();

        gridOffsetX = (getWidth() - grid.getCols() * tileSize) / 2f;
        gridOffsetY = (getHeight() - grid.getRows() * tileSize) / 2f;

        // Use the pluggable WorldRenderer to draw the grid
        worldRenderer.render(grid, gridOffsetX, gridOffsetY,null, null);
    }

    public float getGridOffsetX() {
        return gridOffsetX;
    }

    public float getGridOffsetY() {
        return gridOffsetY;
    }
}
