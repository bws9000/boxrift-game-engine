package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;



import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.block.Block;


public final class CollapseRule extends BaseRule {

    public CollapseRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.RESOLUTION;
    }

    @Override
    public int priority() {
        return 80; // after RowClearRule
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        if (!state.isStructureDirty()) return;

        Grid grid = state.getGrid();
        boolean moved;

        do {
            moved = false;

            // bottom-up scan--- from second-to-last row up to top
            for (int y = grid.getHeight() - 2; y >= 0; y--) {
                for (int x = 0; x < grid.getWidth(); x++) {

                    // if there's a block and the space below is empty, drop it down 1
                    if (!grid.isEmpty(x, y) && grid.isEmpty(x, y + 1)) {
                        Block block = grid.peek(x, y);
                        grid.placeBlock(x, y, null);
                        grid.placeBlock(x, y + 1, block);
                        moved = true;
                    }
                }
            }

        } while (moved);

        state.clearStructureDirty();
    }
}
