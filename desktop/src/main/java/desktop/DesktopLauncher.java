package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.PathPuzzleGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("P'J Cafe - Puzzle Game");
        config.setWindowedMode(800, 600);
        config.useVsync(true);
        new Lwjgl3Application(new PathPuzzleGame(), config);
    }
}
