package core;

public class Tile {
    private final TileType type;
    private int rotation; // 0, 90, 180, 270

    public Tile(TileType type) {
        this.type = type;
        this.rotation = 0;
    }

    public void rotateClockwise() {
        rotation = (rotation + 90) % 360;
    }

    public void rotateCounterClockwise() {
        rotation = (rotation - 90 + 360) % 360;
    }

    public int getRotation() {
        return rotation;
    }

    public TileType getType() {
        return type;
    }
}
