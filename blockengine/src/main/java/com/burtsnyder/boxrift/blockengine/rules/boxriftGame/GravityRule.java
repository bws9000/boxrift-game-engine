package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;

import java.util.function.LongSupplier;

import static com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext.Inhibition.GRAVITY;

public class GravityRule extends BaseRule {

    private final LongSupplier clockNanos;
    private final long intervalNanos;
    private long lastDropAt = 0;

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.SIMULATION;
    }


    public GravityRule(GameState state, int cellsPerSecond) {
        this(state, System::nanoTime, cellsPerSecond);
    }

    /**
     * @param clockNanos time source (for testing / determinism)
     * @param cellsPerSecond gravity speed in grid space (cells/sec)
     */
    public GravityRule(GameState state,
                       LongSupplier clockNanos,
                       int cellsPerSecond) {
        super(state);
        this.clockNanos = clockNanos;
        this.intervalNanos = 1_000_000_000L / cellsPerSecond;
    }

    @Override
    public int priority() {
        return 50;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        var piece = state.getActivePiece();
        if (piece == null) return;

        boolean canFall = state.canMoveActive(0, 1);

        // observe grounded state
        if (!canFall) {
            ctx.currentFrame().setGravityBlocked(true);
        }

        // inhibition
        if (ctx.isInhibited(GRAVITY)) return;

        long now = clockNanos.getAsLong();
        if (now - lastDropAt < intervalNanos) return;

        if (canFall) {
            state.setActivePiece(piece.move(0, 1));
        }

        // update timestamp once interval elapses (prevents catch-up bursts)
        lastDropAt = now;
    }
}