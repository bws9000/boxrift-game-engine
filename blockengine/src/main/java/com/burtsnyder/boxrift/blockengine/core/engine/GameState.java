package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;

public class GameState {
    private final Grid grid;
    private Boxriftle activePiece;
    private long nextPieceId = 1;
    private long nextGroupId = 1;

    public long generateNextGroupId() {
        return this.nextGroupId++;
    }

    public long generateNextPieceId() {
        return nextPieceId++;
    }

    public GameState(int col, int row) { this.grid = new Grid(col,row);}

    public void setActivePiece(Boxriftle piece) {
        this.activePiece = piece;
    }

    public Boxriftle getActivePiece() {
        return activePiece;
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean isValidPosition(Boxriftle piece) {
        if (piece == null) return false;

        var grid = this.grid;
        var origin = piece.getOrigin();

        return piece.getBlocks().stream().allMatch(block -> {
            int x = origin.x() + block.getPosition().x();
            int y = origin.y() + block.getPosition().y();


            if (x < 0 || x >= grid.getWidth()) return false;

            if (y >= grid.getHeight()) return false;

            if (y < 0) return true;

            return grid.isEmpty(x, y);
        });
    }


    public void lockActivePiece() {
        var origin = activePiece.getOrigin();

        activePiece.getBlocks().forEach(block -> {
            int x = origin.x() + block.getPosition().x();
            int y = origin.y() + block.getPosition().y();

            if (grid.inBounds(x, y)) {
                grid.placeBlock(x, y, block);
            }
        });
    }
}
