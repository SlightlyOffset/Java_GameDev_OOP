package core.rendering;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the GdxRenderer class.
 * Tests LibGDX renderer instantiation.
 */
public class GdxRendererTest {

    @Test
    public void testGdxRendererCreation() {
        GdxRenderer renderer = new GdxRenderer();
        assertNotNull(renderer, "GdxRenderer should be instantiatable");
    }
}
