package com.burtsnyder.boxrift.blockengine.core.board.logic;

import com.burtsnyder.boxrift.blockengine.core.board.Grid;

public final class RowLogic {

    private RowLogic() {}

    public static boolean isFullRow(Grid grid, int y) {
        for (int x = 0; x < grid.getWidth(); x++) {
            if (grid.isEmpty(x, y)) return false;
        }
        return true;
    }

    public static boolean rowAlreadyPending(Grid grid, int y) {
        for (int x = 0; x < grid.getWidth(); x++) {
            var block = grid.peek(x, y);
            if (block == null || !block.getMetadata().pendingClear()) {
                return false;
            }
        }
        return true;
    }

    public static void markRowPending(Grid grid, int y) {
        for (int x = 0; x < grid.getWidth(); x++) {
            var block = grid.peek(x, y);
            if (block != null) {
                grid.placeBlock(
                        x, y,
                        block.withMetadata(
                                block.getMetadata().markPendingClear()
                        )
                );
            }
        }
    }

    public static void clearPendingFlags(Grid grid) {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                var block = grid.peek(x, y);
                if (block != null && block.getMetadata().isPendingClear()) {
                    grid.placeBlock(
                            x, y,
                            block.withMetadata(block.getMetadata().clearPending())
                    );
                }
            }
        }
    }

}

