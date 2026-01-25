package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy.RowClearPolicy;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.RowClearKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic.isFullRow;
import static com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic.rowAlreadyPending;

public final class RowClearEligibilityRule extends BaseRule {

    private static final Logger log =
            LoggerFactory.getLogger(RowClearEligibilityRule.class);


    public RowClearEligibilityRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.ENGINE;
    }

    @Override
    public int priority() {
        return 70;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {

        Grid grid = state.getGrid();

        for (int y = 0; y < grid.getHeight(); y++) {

            if (!isFullRow(grid, y)) continue;
            if (rowAlreadyPending(grid, y)) continue;

            RowLogic.markRowPending(grid, y);
            state.markBlinkStarted(y, state.tick);

            //GameState.RowClearKey key = new GameState.RowClearKey(y);
            RowClearKey key = new RowClearKey(y);
            state.rowClearGate().arm(key);
            state.rowClearGate().delay(key, RowClearPolicy.blinkTicks(state.gameMode()));

        }
    }

}


