package core.mechanics;

import java.util.Random;

public class Grid {
    private int cols;
    private int rows;
    private Tile[][] tiles;
    private boolean isSolved = false;

    // start(x, y), end(x, y)
    private int startX, startY, endX, endY;

    public Grid() {
        this.cols = 0;
        this.rows = 0;
    }

    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        tiles = new Tile[rows][cols];

        // กำหนดจุดเริ่มต้น (ซ้ายล่าง) และจุดหมาย (ขวาบน) เป็นค่าเริ่มต้น
        this.startX = 0;
        this.startY = 0;
        this.endX = cols - 1;
        this.endY = rows - 1;
    }

    public void setStartAndEnd(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        this.isSolved = solved;
    }

    // Randomly initialize tiles <-- for testing purposes
    public void randomInitTile() {
        TileType[] types = TileType.values();
        Random random = new Random();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Tile tile = new Tile(types[random.nextInt(types.length)]);
                for (int r = 0; r < random.nextInt(4); r++) {
                    tile.rotateClockwise();
                }
                tiles[y][x] = tile;
            }
        }
    }

    public boolean isPathComplete() {
        if (rows == 0 || cols == 0) {
            return false;
        }
        boolean[][] visited = new boolean[rows][cols];
        // เริ่มต้นหาเส้นทางจาก Start ที่ตั้งไว้
        return hasPath(startY, startX, visited);
    }

    private boolean hasPath(int r, int c, boolean[][] visited) {
        if (r < 0 || r >= rows || c < 0 || c >= cols || visited[r][c]) {
            return false;
        } else {
            visited[r][c] = true;
        }

        // ตรวจสอบว่าถึงจุด End หรือยัง
        if (r == endY && c == endX) {
            return true;
        }

        boolean[] currentConnection = tiles[r][c].getType().getConnections(tiles[r][c].getRotation());

        // --- แก้ไขทิศทางให้ตรงกับ LibGDX ตรงนี้ครับ ---

        // 0: ทิศเหนือ (พอร์ตชี้ขึ้น) ต้องไปตรวจ Tile แถวบน (r + 1) พอร์ตทิศใต้ (2)
        if (currentConnection[0] && canConnect(r + 1, c, 2)) {
            if (hasPath(r + 1, c, visited))
                return true;
        }

        // 1: ทิศตะวันออก (พอร์ตชี้ขวา) ต้องไปตรวจ Tile คอลัมน์ขวา (c + 1)
        // พอร์ตทิศตะวันตก (3)
        if (currentConnection[1] && canConnect(r, c + 1, 3)) {
            if (hasPath(r, c + 1, visited))
                return true;
        }

        // 2: ทิศใต้ (พอร์ตชี้ลง) ต้องไปตรวจ Tile แถวล่าง (r - 1) พอร์ตทิศเหนือ (0)
        if (currentConnection[2] && canConnect(r - 1, c, 0)) {
            if (hasPath(r - 1, c, visited))
                return true;
        }

        // 3: ทิศตะวันตก (พอร์ตชี้ซ้าย) ต้องไปตรวจ Tile คอลัมน์ซ้าย (c - 1)
        // พอร์ตทิศตะวันออก (1)
        if (currentConnection[3] && canConnect(r, c - 1, 1)) {
            if (hasPath(r, c - 1, visited))
                return true;
        }

        return false;
    }

    public boolean canConnect(int nextR, int nextC, int oppositeSide) {
        if (nextR < 0 || nextR >= rows || nextC < 0 || nextC >= cols) {
            return false;
        }
        Tile nexTile = tiles[nextR][nextC];
        return nexTile.getType().getConnections(nexTile.getRotation())[oppositeSide];
    }
}