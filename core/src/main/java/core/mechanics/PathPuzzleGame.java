package core.mechanics;

import com.badlogic.gdx.Game;
import core.windows.GameScreen;

public class PathPuzzleGame extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        // delegate to GameScreen.render()
    }
}
