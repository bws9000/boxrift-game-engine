package com.burtsnyder.boxrift.blockengine.rules;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import static com.burtsnyder.boxrift.blockengine.core.rules.RuleContext.Inhibition.GRAVITY;

public class SoftDropRule extends BaseRule {
    public SoftDropRule(GameState state) {
        super(state);
    }

    @Override public int priority() { return 25; } // after lateral 0, before gravity  50

    @Override
    public void apply(GameState state, RuleContext ctx) {
        boolean dropped = false;
        while (ctx.input().consumeIf(a -> a == InputAction.SOFT_DOWN)) {
            if (tryDown(state)) dropped = true;
            else break; // reached floor no more...
        }
        if (dropped) ctx.inhibit(GRAVITY);
    }

    private boolean tryDown(GameState state) {
        var piece = state.getActivePiece();
        if (piece == null) return false;
        var down = piece.move(0, 1);

        // todo: collision det
        if (!inBounds(state, down)) return false;

        state.setActivePiece(down);
        return true;
    }

    private boolean inBounds(GameState state, com.burtsnyder.boxrift.blockengine.core.actor.Actor actor) {
        var g = state.getGrid();
        var o = actor.getOrigin();
        for (var b : actor.getBlocks()) {
            var p = b.getPosition();
            int x = o.x() + p.x(), y = o.y() + p.y();
            if (x < 0 || x >= g.getWidth() || y < 0 || y >= g.getHeight()) return false;
        }
        return true;
    }
}
