package core.mechanics;

/**
 * Abstract base class for all tile types in the puzzle game.
 * Provides common properties and behavior shared by all tile implementations.
 */
public abstract class BaseTile {
    protected TileType type;
    protected int rotation;

    public BaseTile() {
        this.type = TileType.STRAIGHT;
        this.rotation = 0;
    }
    public BaseTile(TileType type) {
        this.type = type;
        this.rotation = 0;
    }
    public int getRotation() {
        return rotation;
    }
    public TileType getType() {
        return type;
    }
}
