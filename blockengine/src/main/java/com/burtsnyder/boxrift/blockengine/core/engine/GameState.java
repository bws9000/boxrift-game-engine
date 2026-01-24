
package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.engine.base.AbstractGameState;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.LockGate;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.RowClearGate;
import com.burtsnyder.boxrift.blockengine.core.engine.state.mutations.GatedMutation;
import com.burtsnyder.boxrift.blockengine.util.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameState extends AbstractGameState {

    private boolean gameOver = false;
    private final LockGate lockGate;
    private final List<GatedMutation> mutations = new ArrayList<>();
    private final RowClearGate rowClearGate;

    private long nextPieceId = 1;
    private long nextGroupId = 1;

    public GameState(int col, int row) {
        super(new Grid(col, row));
        this.lockGate = new LockGate(500); // delay time
        this.rowClearGate = new RowClearGate(600); // blinker
    }

    //gate accessors
    public LockGate lockGate() {
        return lockGate;
    }

    public RowClearGate rowClearGate() {
        return rowClearGate;
    }

    public void addMutation(GatedMutation mutation) {
        mutations.add(mutation);
    }

    public void tickMutations() {
        mutations.removeIf(m -> {
            m.tick();
            return m.isFinished();
        });
    }

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
        lockGate.reset();
    }


    public void lockActivePieceAndDisassemble() {

        // If there's nothing to lock, do nothing
        if (activePiece == null) {
            return;
        }

        // Arm the gate once
        lockGate.arm();


        if (!lockGate.tryOpen()) {
            return;
        }

        // --- LOCK OCCURS EXACTLY ONCE ---
        var origin = activePiece.getOrigin();
        activePiece.getBlocks().forEach(block -> {
            int x = origin.x() + block.position().x();
            int y = origin.y() + block.position().y();
            if (grid.inBounds(x, y)) {
                grid.placeBlock(x, y, block);
            }
        });

        clearActivePiece();

        // Reset AFTER successful lock
        lockGate.reset();
    }


    public boolean canMoveHorizontally(int dx) {
        var piece = getActivePiece();
        if (piece == null) return false;

        var moved = piece.move(dx, 0);
        return grid.canPlace(moved);
    }

/*    public boolean isGameOver() {
        return gameOver;
    }*/
    public void setGameOver() {
        System.out.println("Game over ");//🕹️implement a Dialog(libGDX) or do Javafx version voila
        this.gameOver = true;
    }






}

