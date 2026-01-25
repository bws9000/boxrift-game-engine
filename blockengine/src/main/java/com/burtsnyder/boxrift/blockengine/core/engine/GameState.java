package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.config.BlockScale;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.engine.base.AbstractGameState;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.DefaultTransitionGate;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.MultiKeyPersistentGate;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.SingleActiveTransitionGate;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.TransitionGate;
import com.burtsnyder.boxrift.blockengine.core.block.Coord;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.LockKey;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.RowClearKey;
import com.burtsnyder.boxrift.blockengine.core.engine.state.GameMode;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.VoidKey;


import java.util.List;

public class GameState extends AbstractGameState {
    //private LockKey lockKey;

    private final TransitionGate<LockKey> lockGate =
            new SingleActiveTransitionGate<>();

    private final TransitionGate<RowClearKey> rowClearGate =
            new MultiKeyPersistentGate<>();

    private final TransitionGate<VoidKey> spawnGate =
            new SingleActiveTransitionGate<>();

    public TransitionGate<VoidKey> spawnGate() {
        return spawnGate;
    }


    public TransitionGate<RowClearKey> rowClearGate() {
        return rowClearGate;
    }

    public TransitionGate<LockKey> lockGate() {
        return lockGate;
    }


    @Override
    public void tickTransitions() {
        rowClearGate.tick();
        lockGate.tick();
        spawnGate.tick();
    }

    private long nextPieceId = 1;
    private long nextGroupId = 1;

    public GameState(int cols, int rows) {
        super(
                new Grid(cols, rows),
                GameMode.CLASSIC,
                BlockScale.MEDIUM
        );
    }

    /*public GameState(int cols, int rows) {
        super(new Grid(cols, rows), GameMode.CLASSIC);
        //this.rowClearGate = new RowClearGate(600); // blinker
    }*/

    public long generateNextGroupId() {
        return nextGroupId++;
    }

    public long generateNextPieceId() {
        return nextPieceId++;
    }

    public List<Coord> getRotationMoves() {
        return List.of(
                new Coord(0, 0),
                new Coord(1, 0),
                new Coord(-1, 0),
                new Coord(0, -1)
        );
    }


    public void markPlayerIntentThisTick() {
        movedThisTick = true;
    }


    public void lockActivePieceAndDisassemble() {

        if (activePiece == null) {
            return;
        }


        var origin = activePiece.getOrigin();
        activePiece.getBlocks().forEach(block -> {
            int x = origin.x() + block.position().x();
            int y = origin.y() + block.position().y();
            if (grid.inBounds(x, y)) {
                grid.placeBlock(x, y, block);
            }
        });

        clearActivePiece();


    }


    public boolean canMoveHorizontally(int dx) {
        var piece = getActivePiece();
        if (piece == null) return false;

        var moved = piece.move(dx, 0);
        return grid.canPlace(moved);
    }


    public void setGameOver() {
        boolean gameOver = true;
    }

}

