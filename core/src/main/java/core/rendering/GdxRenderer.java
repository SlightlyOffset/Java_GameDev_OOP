package core.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * A libGDX-specific implementation of the {@link IRenderer} interface.
 * This class uses a {@link ShapeRenderer} to draw geometric shapes, providing a
 * high-performance rendering backend for the game.
 */
public class GdxRenderer extends BaseRenderer {
    private ShapeRenderer shapeRenderer;

    /**
     * Sets the {@link ShapeRenderer} instance to be used for all drawing operations.
     * This method allows the GameScreen to provide its own managed ShapeRenderer.
     * @param shapeRenderer The ShapeRenderer instance.
     */
    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    /**
     * Clears the screen with the specified color.
     * @param r The red component (0.0 - 1.0).
     * @param g The green component (0.0 - 1.0).
     * @param b The blue component (0.0 - 1.0).
     * @param a The alpha component (0.0 - 1.0).
     */
    @Override
    public void clearScreen(float r, float g, float b, float a) {
        if (Gdx.gl == null) return;
        Gdx.gl.glClearColor(r, g, b, a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Draws a solid rectangle.
     * @param x The x-coordinate of the bottom-left corner.
     * @param y The y-coordinate of the bottom-left corner.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param colorHex The color in hex format (e.g., "#RRGGBB").
     */
    @Override
    public void drawRect(float x, float y, float width, float height, String colorHex) {
        if (shapeRenderer == null) return;
        shapeRenderer.setColor(Color.valueOf(colorHex));
        shapeRenderer.rect(x, y, width, height);
    }

    /**
     * Draws a line representing a path segment, extending from the center of a tile.
     * @param cx The center x-coordinate of the tile.
     * @param cy The center y-coordinate of the tile.
     * @param halfWidth The half-width of the line, which determines its thickness.
     * @param length The length of the line from the center to the edge.
     * @param direction The direction of the line (0: North, 1: East, 2: South, 3: West).
     * @param colorHex The color in hex format (e.g., "#RRGGBB").
     */
    @Override
    public void drawPathLine(float cx, float cy, float halfWidth, float length, int direction, String colorHex) {
        if (shapeRenderer == null) return;
        shapeRenderer.setColor(Color.valueOf(colorHex));

        // In libGDX, Y increases upward.
        // 0=N, 1=E, 2=S, 3=W
        switch (direction) {
            case 0: // North (Up)
                shapeRenderer.rect(cx - halfWidth, cy, halfWidth * 2, length);
                break;
            case 1: // East (Right)
                shapeRenderer.rect(cx, cy - halfWidth, length, halfWidth * 2);
                break;
            case 2: // South (Down)
                shapeRenderer.rect(cx - halfWidth, cy - length, halfWidth * 2, length);
                break;
            case 3: // West (Left)
                shapeRenderer.rect(cx - length, cy - halfWidth, length, halfWidth * 2);
                break;
        }
    }
}
