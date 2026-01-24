package com.burtsnyder.boxrift.blockengine.core.engine.base;

import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.util.Coord;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractGameState {

    public long tick = 0;
    private boolean structureDirty = false;
    protected final Grid grid;
    protected Boxriftle activePiece;
    protected Coord originAtTickStart;
    protected boolean movedThisTick;


    //
    // DIAGNOSTICS (REAd-only, non-behavioral)
    // for debugging lifecycle stalls (i.e ig... row clear blinking)
    // never affect gameplay logic
    //
    protected final Map<Integer, Long> blinkStartFrame = new HashMap<>();
    public static final int MAX_BLINK_TICKS = 200;


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

    public boolean canMoveActive(int dx, int dy) {
        if (activePiece == null) return false;
        return grid.canPlace(activePiece.move(dx, dy));
    }

    public boolean canSpawn(Boxriftle piece) {
        return grid.canPlace(piece);
    }

    public Coord getDefaultSpawnOrigin() {
        int spawnX = (grid.getWidth() / 2) - 1;
        int spawnY = -2;
        return new Coord(spawnX, spawnY);
    }



    //active pieces
    public Boxriftle getActivePiece() {
        return activePiece;
    }

    public void setActivePiece(Boxriftle piece) {
        this.activePiece = piece;
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



    // read-only diagnostics
    public void markBlinkStarted(int row, long frame) {
        blinkStartFrame.put(row, frame);
    }

    public void clearBlink(int row) {
        blinkStartFrame.remove(row);
    }

    public Optional<Long> getBlinkStartFrame(int row) {
        return Optional.ofNullable(blinkStartFrame.get(row));
    }

}


