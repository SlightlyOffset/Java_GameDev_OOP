package core.mechanics;

public class Tile extends BaseTile implements Rotatable {
    public Tile(TileType type) {
        super(type);
    }
    @Override
    public void rotateClockwise() {
        rotation = (rotation + 90) % 360;
    }
    @Override
    public void rotateCounterClockwise() {
        rotation = (rotation - 90 + 360) % 360;
    }
}
