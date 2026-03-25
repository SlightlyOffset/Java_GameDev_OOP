package core.windows;

import com.badlogic.gdx.Screen;
import core.windows.awt.AwtGameWindow;

import java.awt.*;
import java.io.File;
import java.util.*;

public class LevelSelectionScreen {

    private AwtGameWindow window;
    private java.util.List<String> levels = new ArrayList<>();

    public LevelSelectionScreen(AwtGameWindow window) {
        this.window = window;
        loadLevels();
    }

    private void loadLevels() {
        File folder = new File("assets/levels/");
        File[] files = folder.listFiles();

        if (files == null) return;

        Arrays.sort(files);

        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                levels.add(f.getName());
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < levels.size(); i++) {
            int x = 100 + (i % 4) * 150;
            int y = 150 + (i / 4) * 150;

            g.setColor(Color.GRAY);
            g.fillRect(x, y, 120, 120);

            g.setColor(Color.BLACK);
            g.drawString("Level " + (i + 1), x + 30, y + 60);
        }
    }

    public void mouseClicked(int mx, int my) {
        for (int i = 0; i < levels.size(); i++) {
            int x = 100 + (i % 4) * 150;
            int y = 150 + (i / 4) * 150;

            Rectangle r = new Rectangle(x, y, 120, 120);

            if (r.contains(mx, my)) {
                window.setScreen(
                        new GameScreen(window, "assets/levels/" + levels.get(i))
                );
            }
        }
    }
}
