package core.rendering;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GdxRendererTest {

    @Test
    public void testGdxRendererCreation() {
        GdxRenderer renderer = new GdxRenderer();
        assertNotNull(renderer, "GdxRenderer should be instantiatable");
    }
}
