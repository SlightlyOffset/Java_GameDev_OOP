package core.rendering;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the AwtRenderer class.
 * Tests AWT rendering operations including color parsing and screen clearing.
 */
public class AwtRendererTest {
    private AwtRenderer awtRenderer;
    private StubGraphics2D stubG2d;

    @BeforeEach
    public void setUp() {
        awtRenderer = new AwtRenderer();
        stubG2d = new StubGraphics2D();
        awtRenderer.setGraphics(stubG2d, 800, 600);
    }

    @Test
    public void testDrawRectColorParsing() {
        awtRenderer.drawRect(10, 20, 100, 50, "#FF0000");
        assertEquals(Color.RED, stubG2d.lastColor);
        assertEquals(new Rectangle(10, 20, 100, 50), stubG2d.lastRect);
    }

    @Test
    public void testClearScreen() {
        awtRenderer.clearScreen(1.0f, 1.0f, 1.0f, 1.0f); // White
        assertEquals(Color.WHITE, stubG2d.lastColor);
        assertEquals(new Rectangle(0, 0, 800, 600), stubG2d.lastRect);
    }

    // A very minimal stub for testing AwtRenderer
    private static class StubGraphics2D extends Graphics2D {
        public Color lastColor;
        public Rectangle lastRect;

        @Override public void setColor(Color c) { this.lastColor = c; }
        @Override public void fillRect(int x, int y, int width, int height) { this.lastRect = new Rectangle(x, y, width, height); }
        @Override public void clearRect(int x, int y, int width, int height) {}
        @Override public void setPaintMode() {}
        @Override public void setXORMode(Color c1) {}

        // --- All other methods are no-ops ---
        @Override public void draw(Shape s) {}
        @Override public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) { return false; }
        @Override public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {}
        @Override public void drawRenderedImage(RenderedImage img, AffineTransform xform) {}
        @Override public void drawRenderableImage(RenderableImage img, AffineTransform xform) {}
        @Override public void drawString(String str, int x, int y) {}
        @Override public void drawString(String str, float x, float y) {}
        @Override public void drawString(AttributedCharacterIterator iterator, int x, int y) {}
        @Override public void drawString(AttributedCharacterIterator iterator, float x, float y) {}
        @Override public void drawGlyphVector(GlyphVector g, float x, float y) {}
        @Override public void fill(Shape s) {}
        @Override public boolean hit(Rectangle rect, Shape s, boolean onStroke) { return false; }
        @Override public GraphicsConfiguration getDeviceConfiguration() { return null; }
        @Override public void setComposite(java.awt.Composite comp) {}
        @Override public void setPaint(java.awt.Paint paint) {}
        @Override public void setStroke(Stroke s) {}
        @Override public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {}
        @Override public Object getRenderingHint(RenderingHints.Key hintKey) { return null; }
        @Override public void setRenderingHints(Map<?, ?> hints) {}
        @Override public void addRenderingHints(Map<?, ?> hints) {}
        @Override public RenderingHints getRenderingHints() { return null; }
        @Override public void translate(int x, int y) {}
        @Override public void translate(double tx, double ty) {}
        @Override public void rotate(double theta) {}
        @Override public void rotate(double theta, double x, double y) {}
        @Override public void scale(double sx, double sy) {}
        @Override public void shear(double shx, double shy) {}
        @Override public void transform(AffineTransform Tx) {}
        @Override public void setTransform(AffineTransform Tx) {}
        @Override public AffineTransform getTransform() { return null; }
        @Override public java.awt.Paint getPaint() { return null; }
        @Override public java.awt.Composite getComposite() { return null; }
        @Override public void setBackground(Color color) {}
        @Override public Color getBackground() { return null; }
        @Override public Stroke getStroke() { return null; }
        @Override public void clip(Shape s) {}
        @Override public FontRenderContext getFontRenderContext() { return null; }
        @Override public Graphics create() { return null; }
        @Override public Color getColor() { return null; }
        @Override public Font getFont() { return null; }
        @Override public void setFont(Font font) {}
        @Override public FontMetrics getFontMetrics(Font f) { return null; }
        @Override public Rectangle getClipBounds() { return null; }
        @Override public void clipRect(int x, int y, int width, int height) {}
        @Override public void setClip(int x, int y, int width, int height) {}
        @Override public Shape getClip() { return null; }
        @Override public void setClip(Shape clip) {}
        @Override public void copyArea(int x, int y, int width, int height, int dx, int dy) {}
        @Override public void drawLine(int x1, int y1, int x2, int y2) {}
        @Override public void drawRect(int x, int y, int width, int height) {}
        @Override public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
        @Override public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
        @Override public void drawOval(int x, int y, int width, int height) {}
        @Override public void fillOval(int x, int y, int width, int height) {}
        @Override public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
        @Override public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
        @Override public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {}
        @Override public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {}
        @Override public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {}
        @Override public boolean drawImage(Image img, int x, int y, ImageObserver observer) { return false; }
        @Override public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) { return false; }
        @Override public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) { return false; }
        @Override public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) { return false; }
        @Override public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) { return false; }
        @Override public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) { return false; }
        @Override public void dispose() {}
    }
}
