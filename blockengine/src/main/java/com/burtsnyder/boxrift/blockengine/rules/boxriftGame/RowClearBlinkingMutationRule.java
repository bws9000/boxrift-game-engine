package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;

import com.burtsnyder.boxrift.blockengine.core.rules.transitions.RowClearKey;

public final class RowClearBlinkingMutationRule extends BaseRule {

    public RowClearBlinkingMutationRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.ENGINE;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {

        Grid grid = state.getGrid();

        for (int y = 0; y < grid.getHeight(); y++) {

            if (!RowLogic.isFullRow(grid, y)) {
                continue;
            }

            var key = new RowClearKey(y);

            state.rowClearGate().arm(key);
            markRowPending(grid, y);

        }
    }

    private void markRowPending(Grid grid, int y) {
        for (int x = 0; x < grid.getWidth(); x++) {
            var block = grid.peek(x, y);
            if (block != null) {
                grid.placeBlock(
                        x, y,
                        block.withMetadata(
                                block.getMetadata().markPendingClear()
                        )
                );
            }
        }
    }
}

