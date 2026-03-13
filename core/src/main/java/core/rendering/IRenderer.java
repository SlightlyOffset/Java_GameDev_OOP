package core.rendering;

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
     * Finalize the frame (if needed by backend).
     */
    void endFrame();
}
