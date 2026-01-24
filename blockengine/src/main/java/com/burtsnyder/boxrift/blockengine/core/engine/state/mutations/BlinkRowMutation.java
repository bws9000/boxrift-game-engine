package com.burtsnyder.boxrift.blockengine.core.engine.state.mutations;

import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic;

//effects must die when their semantic justification disappears
public final class BlinkRowMutation implements SourceAction {

    private final Grid grid;
    private final int row;

    private boolean visible = true;
    private long lastToggleNanos = 0;

    private static final long BLINK_INTERVAL_NANOS = 50_000_000L; // fast

    public BlinkRowMutation(Grid grid, int row) {
        this.grid = grid;
        this.row = row;
    }

    @Override
    public void onArm() {
        visible = true;
        lastToggleNanos = 0;
        markBlink(true);
    }


    @Override
    public void onTick() {
        if (!RowLogic.rowAlreadyPending(grid, row)) {
            markBlink(false); // ensure blinking is OFF
            return;
        }

        long now = System.nanoTime();

        if (lastToggleNanos == 0) {
            lastToggleNanos = now;
            return;
        }

        if (now - lastToggleNanos >= BLINK_INTERVAL_NANOS) {
            visible = !visible;
            markBlink(visible);
            lastToggleNanos = now;
        }
    }

    @Override
    public void onRelease() {
        markBlink(false);
    }

    @Override
    public void reset() {
        markBlink(false);
    }

    private void markBlink(boolean on) {
        for (int x = 0; x < grid.getWidth(); x++) {
            Block block = grid.peek(x, row);
            if (block != null) {
                grid.placeBlock(
                        x, row,
                        block.withMetadata(block.getMetadata().withBlink(on))
                );
            }
        }
    }
}



    /*private void markBlink(boolean on) {
        for (int x = 0; x < grid.getWidth(); x++) {
            var block = grid.peek(x, row);
            if (block != null) {
                var newMetadata = block.getMetadata().withBlink(on);
                var newBlock = block.withMetadata(newMetadata);
                grid.placeBlock(x, row, newBlock);
            }
        }
    }*/

    /*private void markBlink(boolean on) {
        for (int x = 0; x < grid.getWidth(); x++) {
            var block = grid.peek(x, row);
            if (block != null) {
                block.getMetadata().setBlink(on);
            }
        }
    }*/
//}
