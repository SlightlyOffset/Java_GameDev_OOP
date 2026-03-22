package core.mechanics;

import java.util.Random;

public class Grid {
    private int cols;
    private int rows;
    private Tile[][] tiles;
    private boolean isSolved = false;
    private static final int UNSET = -1;

    // start(x, y), end(x, y)
    private int startX, startY, endX, endY;

    public Grid() {
        this.cols = 0;
        this.rows = 0;
        this.startX = 0;
        this.startY = 0;
        this.endX = UNSET;
        this.endY = UNSET;
    }

    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        tiles = new Tile[rows][cols];
        this.startX = 0;
        this.startY = 0;
        this.endX = UNSET;
        this.endY = UNSET;
    }

    public void setStartAndEnd(int startX, int startY, int endX, int endY) {
        if (startX < 0 || startX >= cols || startY < 0 || startY >= rows) {
            throw new IllegalArgumentException("Start coordinates out of bounds: (" + startX + ", " + startY + ")");
        }
        if (endX < 0 || endX >= cols || endY < 0 || endY >= rows) {
            throw new IllegalArgumentException("End coordinates out of bounds: (" + endX + ", " + endY + ")");
        }
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
        return endX == UNSET && cols > 0 ? cols - 1 : endX;
    }

    public int getEndY() {
        return endY == UNSET && rows > 0 ? rows - 1 : endY;
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
        if (tiles == null || tiles.length == 0 || tiles[0].length == 0) {
            throw new IllegalArgumentException("tiles must be a non-empty array.");
        }
        this.tiles = tiles;
        this.rows = tiles.length;
        this.cols = tiles[0].length;
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
        int resolvedEndX = (endX == UNSET) ? cols - 1 : endX;
        int resolvedEndY = (endY == UNSET) ? rows - 1 : endY;

        boolean[][] visited = new boolean[rows][cols];
        // เริ่มต้นหาเส้นทางจาก Start ที่ตั้งไว้
        return hasPath(startY, startX, visited, resolvedEndX, resolvedEndY);
    }

    private boolean hasPath(int r, int c, boolean[][] visited, int resolvedEndX, int resolvedEndY) {
        if (r < 0 || r >= rows || c < 0 || c >= cols || visited[r][c]) {
            return false;
        }
        visited[r][c] = true;

        if (r == resolvedEndY && c == resolvedEndX) {
            return true;
        }

        Tile currentTile = tiles[r][c];
        if (currentTile == null || currentTile.getType() == null) {
            return false;
        }
        boolean[] currentConnection = currentTile.getType().getConnections(currentTile.getRotation());


        if (currentConnection[0] && canConnect(r + 1, c, 2) && hasPath(r + 1, c, visited, resolvedEndX, resolvedEndY))
            return true;
        if (currentConnection[1] && canConnect(r, c + 1, 3) && hasPath(r, c + 1, visited, resolvedEndX, resolvedEndY))
            return true;
        if (currentConnection[2] && canConnect(r - 1, c, 0) && hasPath(r - 1, c, visited, resolvedEndX, resolvedEndY))
            return true;
        if (currentConnection[3] && canConnect(r, c - 1, 1) && hasPath(r, c - 1, visited, resolvedEndX, resolvedEndY))
            return true;
        return false;
    }

    public boolean canConnect(int nextR, int nextC, int oppositeSide) {
        if (nextR < 0 || nextR >= rows || nextC < 0 || nextC >= cols) {
            return false;
        }
        Tile nextTile = tiles[nextR][nextC];
        if (nextTile == null || nextTile.getType() == null) {
            return false;
        }
        return nextTile.getType().getConnections(nextTile.getRotation())[oppositeSide];
    }
}