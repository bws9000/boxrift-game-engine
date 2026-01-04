package com.burtsnyder.blockengine.core.engine;

import com.burtsnyder.boxrift.actor.Boxriftle;
import com.burtsnyder.blockengine.core.board.Grid;

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
}
