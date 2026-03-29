package core.rendering;

import com.badlogic.gdx.graphics.Texture;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * AWT-based implementation of the IRenderer interface.
 * Fulfills the project requirement of using standard Java Graphics without frameworks.
 */
public class AwtRenderer extends BaseRenderer {
    private Graphics2D g2d;
    private int screenWidth;
    private int screenHeight;

    public void setGraphics(Graphics2D g2d, int screenWidth, int screenHeight) {
        this.g2d = g2d;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public void clearScreen(float r, float g, float b, float a) {
        if (g2d == null) return;
        g2d.setColor(new Color(r, g, b, a));
        g2d.fillRect(0, 0, screenWidth, screenHeight);
    }

    @Override
    public void drawRect(float x, float y, float width, float height, String colorHex) {
        if (g2d == null) return;
        g2d.setColor(Color.decode(colorHex));
        g2d.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void drawPathLine(float cx, float cy, float halfWidth, float length, int direction, String colorHex) {
        if (g2d == null) return;
        g2d.setColor(Color.decode(colorHex));
        
        // 0=N, 1=E, 2=S, 3=W
        switch (direction) {
            case 0: // North
                g2d.fillRect((int) (cx - halfWidth), (int) (cy - length), (int) (halfWidth * 2), (int) length);
                break;
            case 1: // East
                g2d.fillRect((int) cx, (int) (cy - halfWidth), (int) length, (int) (halfWidth * 2));
                break;
            case 2: // South
                g2d.fillRect((int) (cx - halfWidth), (int) cy, (int) (halfWidth * 2), (int) length);
                break;
            case 3: // West
                g2d.fillRect((int) (cx - length), (int) (cy - halfWidth), (int) length, (int) (halfWidth * 2));
                break;
        }
    }

    /**
     * Draws a textured image with rotation and color tint.
     * AWT doesn't natively support LibGDX textures, so this is a stub implementation.
     * In a full AWT implementation, you would convert the Texture to BufferedImage.
     */
    @Override
    public void drawTextureRegion(Texture texture, float x, float y, float originX, float originY,
                                  float width, float height, float rotation,
                                  float colorR, float colorG, float colorB, float colorA) {
        // AWT stub: Texture drawing would require LibGDX → BufferedImage conversion
        // For now, draw a placeholder rectangle to maintain interface contract
        if (g2d == null || texture == null) return;
        
        int argb = (((int) (colorA * 255)) << 24) |
                   (((int) (colorR * 255)) << 16) |
                   (((int) (colorG * 255)) << 8) |
                   ((int) (colorB * 255));
        
        g2d.setColor(new Color(argb, true));
        
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(x + originX, y + originY);
        g2d.rotate(Math.toRadians(rotation));
        g2d.fillRect(-(int)(width / 2), -(int)(height / 2), (int)width, (int)height);
        g2d.setTransform(oldTransform);
    }
}


