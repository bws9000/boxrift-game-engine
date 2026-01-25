package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.TransitionPhase;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.RowClearKey;

public final class RowClearMutationRule extends BaseRule {

    public RowClearMutationRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.MUTATION;
    }

    @Override
    public int priority() {
        return 80;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        Grid grid = state.getGrid();

        for (int y = 0; y < grid.getHeight(); y++) {
            RowClearKey key = new RowClearKey(y);

            boolean pending = RowLogic.rowAlreadyPending(grid, y);
            TransitionPhase phase = state.rowClearGate().phase(key);

            // check invariten delete me
            if (pending && phase == TransitionPhase.DISARMED) {
                throw new IllegalStateException(
                        "impossible: row " + y + " pendingClear but gate DISARMED"
                );
            }


            if (!state.rowClearGate().consumeIfReady(key)) {
                continue;
            }

            //clear
            for (int x = 0; x < grid.getWidth(); x++) {
                grid.placeBlock(x, y, null);
            }

            //state.clearBlink(y);
            state.rowClearGate().reset(key);
            state.markStructureDirty();
        }
    }

}

