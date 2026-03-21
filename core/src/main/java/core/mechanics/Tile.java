package core.mechanics;

public class Tile extends BaseTile implements Rotatable {
    public Tile() {
        super();
    }

    public enum Direction {
        // ลำดับที่ 0, 1, 2, 3
        NORTH, EAST, SOUTH, WEST;
        
        public Direction getOpposite() {
            return Direction.values()[(this.ordinal() + 2) % 4];
        }
    }

    public boolean hasConnection(Direction d) {
        boolean[] connections = type.getConnections(rotation);
        return connections[d.ordinal()];
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
