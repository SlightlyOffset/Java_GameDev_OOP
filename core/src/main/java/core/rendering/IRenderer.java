package core.rendering;

import com.badlogic.gdx.graphics.Texture;

/**
 * Custom Interface for pluggable rendering backends.
 * Satisfies OOP Requirement 1.2.
 */
public interface IRenderer {
    /**
     * Clear the screen with the specified color.
     */
    void clearScreen(float r, float g, float b, float a);

    /**
     * Draw a solid rectangle.
     */
    void drawRect(float x, float y, float width, float height, String colorHex);

    /**
     * Draw a path line from center in the given direction.
     */
    void drawPathLine(float cx, float cy, float halfWidth, float length, int direction, String colorHex);

    /**
     * Draw a textured sprite with rotation and color tint.
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
    void drawTextureRegion(Texture texture, float x, float y, float originX, float originY,
                          float width, float height, float rotation,
                          float colorR, float colorG, float colorB, float colorA);
    
    /**
     * Finalize the frame (if needed by backend).
     */
    void endFrame();
}
