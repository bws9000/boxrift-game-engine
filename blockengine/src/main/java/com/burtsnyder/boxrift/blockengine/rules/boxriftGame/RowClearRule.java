package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic.isFullRow;
import static com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic.rowAlreadyPending;

/* this rule is connected with blink rule */
public final class RowClearRule extends BaseRule {

    private static final Logger log =
            LoggerFactory.getLogger(RowClearRule.class);


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
        boolean gateOpen = state.rowClearGate().tryOpen();

        if (!gateOpen) {
            log.debug(
                    "RowClearRule skipped (gate closed) tick={} gate={}",
                    state.tick,
                    System.identityHashCode(state.rowClearGate())
            );
            return;
        }

        // from here on, TRUSTING.. gateOpen
        Grid grid = state.getGrid();
        boolean clearedAny = false;

        RowLogic.clearPendingFlags(grid);
        for (int y = 0; y < grid.getHeight(); y++) {
            state.clearBlink(y);
        }

        for (int y = 0; y < grid.getHeight(); y++) {
            if (!RowLogic.isFullRow(grid, y)) continue;

            for (int x = 0; x < grid.getWidth(); x++) {
                grid.placeBlock(x, y, null);
            }

            clearedAny = true;
        }

        if (clearedAny) {
            state.markStructureDirty();
            state.rowClearGate().reset();
            log.debug("RowClearGate CLOSED tick={} reason=rowsCleared", state.tick);
        }
    }


    /*@Override
    public void apply(GameState state, RuleContext ctx) {
        //diagnostics
        if (!state.rowClearGate().isOpen()) {
            // only log if we are currently tracking any blink
            for (int y = 0; y < state.getGrid().getHeight(); y++) {
                if (state.getBlinkStartFrame(y).isPresent()) {
                    log.debug("RowClearRule skipped (gate closed) tick={} gate={}",
                            state.tick,
                            System.identityHashCode(state.rowClearGate()));
                    break;
                }
            }
            return;
        }
        // /



        if (!state.rowClearGate().isOpen()) return;

        Grid grid = state.getGrid();
        boolean clearedAny = false;

        // clear ALL pending semantics BEFORE mutating structure
        RowLogic.clearPendingFlags(grid);
        for (int y = 0; y < grid.getHeight(); y++) {
            state.clearBlink(y);
        }

        for (int y = 0; y < grid.getHeight(); y++) {
            if (!isFullRow(grid, y)) continue;

            // rowAlreadyPending is no longer needed here,
            // because pending semantics were just cleared
            for (int x = 0; x < grid.getWidth(); x++) {
                grid.placeBlock(x, y, null);
            }

            clearedAny = true;
        }

        if (clearedAny) {
            state.markStructureDirty();
            state.rowClearGate().reset();
            log.debug("RowClearGate CLOSE requested tick={} reason={}", state.tick, "RowClearBlinkStart");

            log.debug(
                    "RowClear RESOLVED tick={} gateReset=true structureDirty=true",
                    state.tick
            );
            if (RowLogic.anyPendingRows(grid)) {
                log.error("Pending flags remain AFTER RowClearRule — invariant violated");
            }

        }
    }*/
}


