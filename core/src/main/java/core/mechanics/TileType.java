package core.mechanics;

/**
 * Defines the different types of tiles that can exist in the game grid.
 * Each type has a specific connection layout (e.g., straight, L-turn)
 * that determines how paths can be formed.
 */
public enum TileType {
    /** A straight path, typically connecting North and South. */
    STRAIGHT("tiletype/STRAIGHT.PNG"),
    /** An L-shaped turn, typically connecting North and East. */
    L_TURN("tiletype/L_TURN.PNG"),
    /** A T-shaped junction, typically connecting North, East, and West. */
    T_JUNCTION("tiletype/T_JUNCTION.PNG"),
    /** A path with only one connection, a dead end. */
    DEADEND_X("tiletype/DEADEND_X.PNG"),
    /** A path with  only one connection, a dead end. */
    DEADEND_Y("tiletype/DEADEND_Y.PNG"),
    /** A special tile that connects to another teleport tile. */
    TELEPORT("tiletype/TELEPORT.PNG"),
    /** A path that connects in all four directions. */
    CROSS(""),
    /** A decorative tile that looks like a teleport but has no connections. */
    WRONG_TELEPORT("tiletype/WRONG_TELEPORT.PNG"),
    /** An empty tile with no connections. */
    EMPTY(""),
    start("tiletype/start.PNG"),
    end("tiletype/end.PNG");

    private static final boolean[][] PRECOMPUTED = new boolean[TileType.values().length * 4][4];
    private final String textureName;
    // Statically pre-calculates all possible rotation states for every tile type
    // to avoid redundant calculations during runtime.
    TileType(String textureName) {
        this.textureName = textureName;
    }
    static {
        for (TileType type : TileType.values()) {
            boolean[] base = type.getBase();
            for (int steps = 0; steps < 4; steps++) {
                boolean[] rotated = new boolean[4];
                for (int i = 0; i < 4; i++) {
                    rotated[(i + steps) % 4] = base[i];
                }
                PRECOMPUTED[type.ordinal() * 4 + steps] = rotated;
            }
        }
    }

    /**
     * Returns the base connection layout for a tile type at 0-degree rotation.
     * The boolean array represents connections for [North, East, South, West].
     * @return A boolean array of size 4 representing the base connections.
     */
    public String getTextureName() {
        return textureName;
    }
    private boolean[] getBase() {
        switch (this) {
            case STRAIGHT:
                return new boolean[] { true, false, true, false };
            case L_TURN:
                return new boolean[] { true, true, false, false };
            case T_JUNCTION:
                return new boolean[] { true, true, false, true };
            case DEADEND_X:
            case DEADEND_Y:
                return new boolean[] { true, false, false, false };
            case start:
                return new boolean[] { true, true, true, true };
            case end:
                return new boolean[] { true, true, true, true };
            case TELEPORT:
                return new boolean[] { true, true, true, true };
            case CROSS:
                return new boolean[] { true, true, true, true };
            default:
                return new boolean[] { false, false, false, false };
        }
    }

    /**
     * Retrieves the connection layout for this tile type at a specific rotation.
     * It uses the pre-computed table for efficient lookup.
     * @param rotation The rotation of the tile in degrees (e.g., 0, 90, 180, 270).
     * @return A boolean array of size 4 representing the connections for [N, E, S, W].
     */
    public boolean[] getConnections(int rotation) {
        int steps = (rotation / 90) % 4;
        return PRECOMPUTED[this.ordinal() * 4 + steps];
    }
}
