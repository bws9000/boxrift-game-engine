package com.burtsnyder.boxrift.blockengine.core.engine.state.mutations;


import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;

public record BlinkRowMutation(int row, boolean blinkOn)
        implements GameMutation {

    @Override
    public void apply(GameState state) {
        Grid grid = state.getGrid();

        for (int x = 0; x < grid.getWidth(); x++) {
            Block block = grid.peek(x, row);
            if (block != null) {
                grid.placeBlock(
                        x, row,
                        block.withMetadata(
                                block.getMetadata().withBlink(blinkOn)
                        )
                );
            }
        }
    }
}
