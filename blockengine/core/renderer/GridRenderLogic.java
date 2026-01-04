package com.burtsnyder.blockengine.core.renderer;

import com.burtsnyder.blockengine.core.board.Grid;
import com.burtsnyder.blockengine.util.Coord;

import java.util.ArrayList;
import java.util.List;

public class GridRenderLogic {
    public static List<GridRenderCell> generate(Grid grid, int blockSize) {
        List<GridRenderCell> cells = new ArrayList<>();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                int px = x * blockSize;
                int py = y * blockSize;
                cells.add(new GridRenderCell(new Coord(x, y), px, py, blockSize, blockSize));
            }
        }
        return cells;
    }
}
