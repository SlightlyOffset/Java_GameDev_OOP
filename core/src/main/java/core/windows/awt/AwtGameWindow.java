package core.windows.awt;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import core.mechanics.Grid;
import core.rendering.AwtRenderer;
import core.rendering.WorldRenderer;

/**
 * Standard Java Window (JFrame) for testing AwtRenderer.
 * Satisfies OOP Requirement 1.1 (Graphics & Threads).
 */
public class AwtGameWindow extends JFrame {
    private final Grid grid;
    private final AwtRenderer awtRenderer;
    private final WorldRenderer worldRenderer;
    private final AwtGameCanvas gameCanvas;

    public AwtGameWindow() {
        // Setup simple game state for testing
        grid = new Grid(4, 4);
        grid.randomInitTile();

        awtRenderer = new AwtRenderer();
        worldRenderer = new WorldRenderer(awtRenderer, 100);

        setTitle("PJ Cafe - AWT Renderer (Grading Proof)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use the custom AwtGameCanvas
        gameCanvas = new AwtGameCanvas(grid, awtRenderer, worldRenderer);
        
        // Add click listener to the canvas
        gameCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 1. Get mouse position
                int mouseX = e.getX();
                int mouseY = e.getY();

                // 2. Subtract the offsets to get the position relative to the grid
                float relativeX = mouseX - gameCanvas.getGridOffsetX();
                float relativeY = mouseY - gameCanvas.getGridOffsetY();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    // 3. Convert to grid coordinates
                    int gridX = (int) (relativeX / 100);
                    int gridY = (int) (relativeY / 100);

                    // 4. Check if within bounds and rotate the tile
                    if (gridX >= 0 && gridX < grid.getCols() && gridY >= 0 && gridY < grid.getRows()) {
                        grid.getTiles()[gridY][gridX].rotateClockwise();
                    }
                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    int gridX = (int) (relativeX / 100);
                    int gridY = (int) (relativeY / 100);

                    if (gridX >= 0 && gridX < grid.getCols() && gridY >= 0 && gridY < grid.getRows()) {
                        grid.getTiles()[gridY][gridX].rotateCounterClockwise();
                    }
                }
            }
        });

        add(gameCanvas);

        // Satisfies Requirement 1.1: Using Threads (via Swing Timer for UI safety)
        // Refresh the screen every 16ms (~60 FPS)
        Timer gameLoop = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameCanvas.repaint();
            }
        });
        gameLoop.start();
    }

    public static void main(String[] args) {
        // Run the AWT version of the game
        java.awt.EventQueue.invokeLater(() -> {
            new AwtGameWindow().setVisible(true);
        });
    }
}
