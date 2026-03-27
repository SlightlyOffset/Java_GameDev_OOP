package core.rendering;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the WorldRenderer class.
 * Tests rendering delegation to backend renderers using mock objects.
 */
public class WorldRendererTest {

    @Test
    public void testClearScreenDelegation() {
        MockRenderer mock = new MockRenderer();
        WorldRenderer worldRenderer = new WorldRenderer(mock, 100);
        
        worldRenderer.clearScreen();
        
        assertTrue(mock.clearScreenCalled, "clearScreen should be delegated to the renderer");
    }

    private static class MockRenderer implements IRenderer {
        public boolean clearScreenCalled = false;

        @Override
        public void clearScreen(float r, float g, float b, float a) {
            clearScreenCalled = true;
        }

        @Override
        public void drawRect(float x, float y, float width, float height, String colorHex) {}

        @Override
        public void drawPathLine(float cx, float cy, float halfWidth, float length, int direction, String colorHex) {}

        @Override
        public void endFrame() {}
    }
}
