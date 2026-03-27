package desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import core.mechanics.PathPuzzleGame;

/**
 * Desktop launcher for the P'J Cafe puzzle game.
 * Configures and starts the LibGDX application with window settings.
 */
public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("P'J Cafe - Puzzle Game");
        config.setWindowedMode(1920 , 1080);
        config.setResizable(false);
        config.useVsync(true);
        new Lwjgl3Application(new PathPuzzleGame(), config);
    }
}
