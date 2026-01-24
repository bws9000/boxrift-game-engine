package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.board.logic.RowLogic;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.engine.state.mutations.BlinkRowMutation;
import com.burtsnyder.boxrift.blockengine.core.engine.state.mutations.GatedMutation;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RowClearBlinkingMutationRule extends BaseRule {

    private static final Logger log =
            LoggerFactory.getLogger(RowClearBlinkingMutationRule.class);

    public RowClearBlinkingMutationRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.MUTATION;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {

        Grid grid = state.getGrid();
        long now = state.tick;

        // ---- WATCH ----
        for (int y = 0; y < grid.getHeight(); y++) {
            int row = y;
            state.getBlinkStartFrame(row).ifPresent(startTick -> {
                long elapsed = now - startTick;
                if (elapsed > GameState.MAX_BLINK_TICKS) {
                    log.error("""
                BLINK STALL DETECTED
                row={}
                elapsedTicks={}
                gateOpen={}
                pending={}
                full={}
                structureDirty={}
                """,
                            row,
                            elapsed,
                            state.rowClearGate().tryOpen(),
                            RowLogic.rowAlreadyPending(grid, row),
                            RowLogic.isFullRow(grid, row),
                            state.isStructureDirty()
                    );
                }
            });
        }
        // ------------------



        for (int y = 0; y < grid.getHeight(); y++) {
            if (!isFullRow(grid, y)) continue;
            if (rowAlreadyPending(grid, y)) continue;

            markRowPending(grid, y);

/*            log.debug(
                    "Row Clear BLINK START row={} tick={} gate={}",
                    y,
                    state.tick,
                    System.identityHashCode(state.rowClearGate())
            );*/
            state.markBlinkStarted(y, state.tick);//diagnostic

            state.addMutation(
                    new GatedMutation(
                            state.rowClearGate(),
                            new BlinkRowMutation(grid, y)
                    )
            );
        }
    }

    /*@Override
    public void apply(GameState state, RuleContext ctx) {
        Grid grid = state.getGrid();

        for (int y = 0; y < grid.getHeight(); y++) {
            if (!isFullRow(grid, y)) continue;

            state.addMutation(
                    new GatedMutation(
                            new RowClearGate(600),
                            new BlinkRowMutation(grid, y)
                    )
            );
        }
    }*/

    private boolean isFullRow(Grid grid, int y) {
        return RowLogic.isFullRow(grid, y);
    }


    private boolean rowAlreadyPending(Grid grid, int y) {
        return RowLogic.rowAlreadyPending(grid, y);
    }

    private void markRowPending(Grid grid, int y) {
        for (int x = 0; x < grid.getWidth(); x++) {
            var block = grid.peek(x, y);
            if (block != null) {
                grid.placeBlock(
                        x, y,
                        block.withMetadata(block.getMetadata().markPendingClear())
                );
            }
        }
    }

}

