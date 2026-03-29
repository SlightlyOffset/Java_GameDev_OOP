package core.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * A libGDX-specific implementation of the {@link IRenderer} interface.
 * This class uses a {@link ShapeRenderer} for geometric shapes and a {@link SpriteBatch}
 * for texture rendering, providing a high-performance rendering backend for the game.
 */
public class GdxRenderer extends BaseRenderer {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    /**
     * Sets the {@link ShapeRenderer} instance to be used for geometric drawing.
     * @param shapeRenderer The ShapeRenderer instance.
     */
    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    /**
     * Sets the {@link SpriteBatch} instance to be used for texture rendering.
     * @param spriteBatch The SpriteBatch instance.
     */
    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
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

    /**
     * Draws a textured sprite with rotation and color tint.
     * @param texture The texture to draw.
     * @param x The x-coordinate of the bottom-left corner.
     * @param y The y-coordinate of the bottom-left corner.
     * @param originX The x-coordinate of the rotation origin.
     * @param originY The y-coordinate of the rotation origin.
     * @param width The width of the sprite.
     * @param height The height of the sprite.
     * @param rotation The rotation in degrees.
     * @param colorR Red component (0.0 - 1.0).
     * @param colorG Green component (0.0 - 1.0).
     * @param colorB Blue component (0.0 - 1.0).
     * @param colorA Alpha component (0.0 - 1.0).
     */
    @Override
    public void drawTextureRegion(Texture texture, float x, float y, float originX, float originY,
                                  float width, float height, float rotation,
                                  float colorR, float colorG, float colorB, float colorA) {
        if (spriteBatch == null || texture == null) return;
        
        spriteBatch.setColor(colorR, colorG, colorB, colorA);
        spriteBatch.draw(
            new TextureRegion(texture),
            x, y,
            originX, originY,
            width, height,
            1f, 1f,
            rotation
        );
    }
}

