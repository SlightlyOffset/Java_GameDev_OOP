package core.mechanics;

/**
 * Represents a single tile on the puzzle grid.
 * Tiles can be rotated and have connections in different directions based on their type and rotation.
 */
public class Tile extends BaseTile implements Rotatable {
    public int targetX;
    public int targetY;
    public Tile() {
        super();
    }

    public enum Direction {
        // ลำดับที่ 0, 1, 2, 3
        NORTH, EAST, SOUTH, WEST;
        
    }


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
