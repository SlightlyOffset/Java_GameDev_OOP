package core;

import core.mechanics.Grid;
import core.rendering.AwtRenderer;
import core.rendering.WorldRenderer;
import core.windows.awt.AwtGameCanvas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the AwtGameCanvas class.
 * Tests AWT canvas instantiation with grid and renderer components.
 */
public class AwtGameCanvasTest {

    @Test
    public void testAwtGameCanvasInstantiation() {
        Grid grid = new Grid(2, 2);
        AwtRenderer awtRenderer = new AwtRenderer();
        WorldRenderer worldRenderer = new WorldRenderer(awtRenderer, 100);
        
        AwtGameCanvas canvas = new AwtGameCanvas(grid, awtRenderer, worldRenderer);
        assertNotNull(canvas);
    }
}
