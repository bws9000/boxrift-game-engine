package com.burtsnyder.boxrift.blockengine.core.board;

import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;
import com.burtsnyder.boxrift.blockengine.core.block.Block;

public class Grid {
    private final int width;
    private final int height;
    private final Cell[][] cells;

    public Block peek(int x, int y) {
        if (!inBounds(x, y)) return null;
        return cells[x][y].getBlock();
    }


    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x][y] = new Cell();
            }
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    public boolean isEmpty(int x, int y) {
        return cells[x][y].getBlock() == null;
    }


    public void placeBlock(int x, int y, Block block) {
            if (!inBounds(x, y)) {
                throw new IllegalArgumentException(" Out of bounds: " + x + "," + y);
            }
            cells[x][y].setBlock(block);
    }

    public boolean canPlace(Boxriftle piece) {
        var origin = piece.getOrigin();

        for (var block : piece.getBlocks()) {
            int x = origin.x() + block.position().x();
            int y = origin.y() + block.position().y();

            // horizontal
            if (x < 0 || x >= width) return false;

            // bottom
            if (y >= height) return false;

            // spawn zone above grid
            if (y < 0) {
                if (!isEmpty(x, 0)) return false;
                continue;
            }

            //  inside
            if (!isEmpty(x, y)) return false;
        }

        return true;
    }


}
