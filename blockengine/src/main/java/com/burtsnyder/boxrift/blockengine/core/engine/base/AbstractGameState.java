package com.burtsnyder.boxrift.blockengine.core.engine.base;

import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;
import com.burtsnyder.boxrift.blockengine.util.Coord;

public abstract class AbstractGameState {
    protected Coord originAtTickStart;
    protected boolean movedThisTick;

    protected void beginTickInternal(Boxriftle activePiece) {
        movedThisTick = false;
        originAtTickStart = activePiece != null
                ? activePiece.getOrigin()
                : null;
    }

    protected void notifyPieceMovedInternal() {
        movedThisTick = true;
    }

    public boolean didPieceMove() {
        return movedThisTick;
    }

    public boolean originUnchanged(Boxriftle activePiece) {
        if (activePiece == null || originAtTickStart == null) return false;
        return activePiece.getOrigin().equals(originAtTickStart);
    }
}

