package core.mechanics;

import java.util.Random;

public class Grid {
    private int cols;
    private int rows;
    private Tile[][] tiles;

    public Grid() {
        this.cols = 0;
        this.rows = 0;
    }

    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        tiles = new Tile[rows][cols];
    }

    public int getCols() {
        if (tiles != null && tiles.length > 0) return tiles[0].length;
        return cols;
    }

    public int getRows() {
        if (tiles != null) return tiles.length; // ให้นับจากแถวที่มีอยู่จริง
        return rows;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    // Randomly initialize tiles <-- for testing purposes
    public void randomInitTile() {
        TileType[] types = TileType.values();
        Random random = new Random();
        for (int y=0; y<rows; y++) {
            for (int x=0; x<cols; x++) {
                Tile tile = new Tile(types[random.nextInt(types.length)]);
                for (int r=0; r< random.nextInt(4); r++) {tile.rotateClockwise();}
                tiles[y][x] = tile;
            }
        }
    }

}
