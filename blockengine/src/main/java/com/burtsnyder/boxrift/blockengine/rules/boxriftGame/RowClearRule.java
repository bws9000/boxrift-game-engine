package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;

public final class RowClearRule extends BaseRule {
    public RowClearRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.RESOLUTION;
    }

    @Override
    public int priority() {
        return 70; // after lock, before collapse
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        Grid grid = state.getGrid();

        for (int y = 0; y < grid.getHeight(); y++) {
            boolean full = true;

            for (int x = 0; x < grid.getWidth(); x++) {
                if (grid.isEmpty(x, y)) {
                    full = false;
                    break;
                }
            }

            if (full) {
                for (int x = 0; x < grid.getWidth(); x++) {
                    grid.placeBlock(x, y, null);
                }
                state.markStructureDirty();
            }
        }
    }
}

