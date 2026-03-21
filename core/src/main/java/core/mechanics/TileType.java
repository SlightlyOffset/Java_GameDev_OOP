package core.mechanics;

public enum TileType {
    STRAIGHT,
    L_TURN,
    T_JUNCTION,
    CROSS;

    public boolean[] getConnections(int rotation) {
        boolean[] ports = new boolean[4];
        switch (this) {
            case STRAIGHT:
                // เหนือ-ใต้
                ports[0] = ports[2] = true;
                break;
            case L_TURN:
                // เหนือ-ตะวันออก
                ports[0] = ports[1] = true;
                break;
            case T_JUNCTION:
                // เหนือ-ตะวันออก-ตะวันตก
                ports[0] = ports[1] = ports[3] = true;
                break;
            case CROSS:
                // ทุกทิศ
                ports[0] = ports[1] = ports[2] = ports[3] = true;
                break;
        }

        int steps = (rotation / 90) % 4;
        boolean[] rotatedPorts = new boolean[4];
        for (int i = 0; i < 4; i++) {
            rotatedPorts[(i + steps) % 4] = ports[i];
        }
        return rotatedPorts;
    }
}