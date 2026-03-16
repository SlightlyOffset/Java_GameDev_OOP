package core.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * libGDX implementation of the IRenderer interface using ShapeRenderer.
 * Used for high-performance rendering.
 */
public class GdxRenderer implements IRenderer {
    private ShapeRenderer shapeRenderer;

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void clearScreen(float r, float g, float b, float a) {
        if (Gdx.gl == null) return;
        Gdx.gl.glClearColor(r, g, b, a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void drawRect(float x, float y, float width, float height, String colorHex) {
        if (shapeRenderer == null) return;
        shapeRenderer.setColor(Color.valueOf(colorHex));
        shapeRenderer.rect(x, y, width, height);
    }

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

    @Override
    public void endFrame() {
        // No-op for GdxRenderer as ShapeRenderer's end() call is typically handled in GameScreen
    }
}
