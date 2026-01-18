package com.burtsnyder.boxrift.blockengine.core.engine.base;

import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.util.Coord;

public abstract class AbstractGameState {

    protected long tick = 0;

/*    public long getTick() {
        return tick;
    }*/

/*    private long lastPlayerIntentTick = -1;*/
    private boolean structureDirty = false;

/*
    public void markPlayerIntentThisTick() {
        lastPlayerIntentTick = tick;
    }

    public long lastPlayerIntentTick() {
        return lastPlayerIntentTick;
    }
*/



    protected final Grid grid;
    protected Boxriftle activePiece;
    protected Coord originAtTickStart;
    protected boolean movedThisTick;


    public Grid getGrid() {
        return grid;
    }

    // gravity result for this tick
    protected boolean downwardBlockedThisTick;

    protected AbstractGameState(Grid grid) {
        this.grid = grid;
    }


    public void beginTickInternal(Boxriftle activePiece) {
        tick++;
        movedThisTick = false;
        downwardBlockedThisTick = false;
        originAtTickStart = activePiece != null
                ? activePiece.getOrigin()
                : null;
    }
    /*
    public void beginTickInternal(Boxriftle activePiece) {
        movedThisTick = false;
        downwardBlockedThisTick = false;

        originAtTickStart = activePiece != null
                ? activePiece.getOrigin()
                : null;
    }*/

    public void notifyPieceMovedInternal() {
        movedThisTick = true;
    }

    //tick queries
    public boolean didPieceMove() {
        return movedThisTick;
    }

    public boolean originUnchanged() {
        if (activePiece == null || originAtTickStart == null) return false;
        return activePiece.getOrigin().equals(originAtTickStart);
    }

    public boolean canMoveActive(int dx, int dy) {
        if (activePiece == null) return false;
        return grid.canPlace(activePiece.move(dx, dy));
    }

    public boolean canSpawn(Boxriftle piece) {
        return grid.canPlace(piece);
    }

/*    public boolean canRotateActive() {
        if (activePiece == null) return false;
        //return grid.canPlace(activePiece.rotate());
        return true;
    }*/

    public Coord getDefaultSpawnOrigin() {
        int spawnX = (grid.getWidth() / 2) - 1;
        int spawnY = -2;
        return new Coord(spawnX, spawnY);
    }


/*    public boolean canSpawn(Boxriftle piece) {
        //return grid.canPlace(piece);
        return false;
    }*/



    //active pieces
    public Boxriftle getActivePiece() {
        return activePiece;
    }

    public void setActivePiece(Boxriftle piece) {
        this.activePiece = piece;
    }

    public void markPlayerMovedThisTick() {
        movedThisTick = true;
    }


    public void clearActivePiece() {
        this.activePiece = null;
    }





    public void markStructureDirty() {
        structureDirty = true;
    }

    public boolean isStructureDirty() {
        return structureDirty;
    }

    public void clearStructureDirty() {
        structureDirty = false;
    }

}


