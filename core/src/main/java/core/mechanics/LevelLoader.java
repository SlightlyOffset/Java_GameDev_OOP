package core.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Utility class for loading and saving puzzle levels from/to JSON files.
 * Handles both LibGDX and non-LibGDX contexts for testing purposes.
 */
public class LevelLoader {

    public static Grid loadLevel(String path) {
        Json json = new Json();
        FileHandle file;
        if (Gdx.files != null) {
            file = Gdx.files.internal(path);
        } else {
            // For unit testing outside of LibGDX context
            file = new FileHandle(path);
        }
        return json.fromJson(Grid.class, file);
    }

    public static String toJson(Grid grid) {
        Json json = new Json();
        return json.toJson(grid);
    }
}
