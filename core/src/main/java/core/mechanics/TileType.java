package core.mechanics;

public enum TileType {
    STRAIGHT,
    L_TURN,
    T_JUNCTION,
    STRAIGHT_ROTATABLE,
    L_TURN_ROTATABLE,
    T_JUNCTION_ROTATABLE,
    DEADEND,
    TELEPORT,
    CROSS,
    WRONG_TELEPORT,
    EMPTY;

    private static final boolean[][] PRECOMPUTED = new boolean[TileType.values().length * 4][4];

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

    private boolean[] getBase() {
        switch (this) {
            case STRAIGHT:
            case STRAIGHT_ROTATABLE:
                return new boolean[] { true, false, true, false };
            case L_TURN:
            case L_TURN_ROTATABLE:
                return new boolean[] { true, true, false, false };
            case T_JUNCTION:
            case T_JUNCTION_ROTATABLE:
                return new boolean[] { true, true, false, true };
            case DEADEND:
                return new boolean[] { true, false, false, false };
            case TELEPORT:
                return new boolean[] { true, true, true, true };
            case CROSS:
                return new boolean[] { true, true, true, true };
            default:
                return new boolean[] { false, false, false, false };
        }
    }

    public boolean[] getConnections(int rotation) {
        int steps = (rotation / 90) % 4;
        return PRECOMPUTED[this.ordinal() * 4 + steps];
    }
}
