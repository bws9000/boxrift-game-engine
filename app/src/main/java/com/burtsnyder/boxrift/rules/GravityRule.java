package com.burtsnyder.boxrift.rules;

import com.burtsnyder.blockengine.core.engine.GameState;
import com.burtsnyder.blockengine.core.rules.BaseRule;
import com.burtsnyder.blockengine.core.rules.RuleContext;
import java.util.function.LongSupplier;
import static com.burtsnyder.blockengine.core.rules.RuleContext.Inhibition.GRAVITY;

public class GravityRule extends BaseRule {
    private final LongSupplier clockNanos;
    private final long intervalNanos;
    private long lastDropAt = 0;

    public GravityRule(GameState state) {
        this(state, System::nanoTime, 1000);
    }
    public GravityRule(GameState state, long gravityMs) {
        this(state, System::nanoTime, gravityMs);
    }

    /**
     * @param gravityMs interval between automatic drops (milliseconds)
     */
    public GravityRule(GameState state, LongSupplier clockNanos, long gravityMs) {
        super(state);
        this.clockNanos= clockNanos;
        this.intervalNanos = gravityMs * 1_000_000L;
    }

    @Override
    public int priority() {
        return 50;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        if (ctx.isInhibited(GRAVITY)) return;// gravity rule inhibited

        long now = clockNanos.getAsLong();
        if (now - lastDropAt < intervalNanos) return;

        var piece = state.getActivePiece();
        if (piece == null) return;

        var down = piece.move(0, 1);

        // todo: trigger lock/land behavior (....state.lockActivePiece())

        state.setActivePiece(down);
        lastDropAt = now;
    }
}
