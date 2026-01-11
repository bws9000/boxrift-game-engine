package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.actor.Actor;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import static com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext.Inhibition.GRAVITY;

public class LateralMoveRule extends BaseRule {
    public LateralMoveRule(GameState state) {
        super(state);
    }

    @Override public int priority() { return 0; }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        boolean movedAny = false;
        while (true) {
            boolean moved =
                    ctx.input().consumeIf(a -> a == InputAction.MOVE_LEFT)  && tryMove(state, -1, 0)
                            || ctx.input().consumeIf(a -> a == InputAction.MOVE_RIGHT) && tryMove(state,  1, 0);

            if (!moved) break;
            movedAny = true;
        }
        if (movedAny) ctx.inhibit(GRAVITY);
    }

    private boolean tryMove(GameState state, int dx, int dy) {
        var piece = state.getActivePiece();
        if (piece == null) return false;
        var moved = piece.move(dx, dy);
        if (!inBounds(state, moved)) return false;   // minimal wall ceileing check
        state.setActivePiece(moved);
        return true;
    }



    private boolean inBounds(GameState state, Actor actor) {
        var g = state.getGrid();
        var o = actor.getOrigin();
        for (var b : actor.getBlocks()) {
            var p = b.getPosition();
            int x = o.x() + p.x();
            int y = o.y() + p.y();
            if (x < 0 || x >= g.getWidth() || y < 0 || y >= g.getHeight()) return false;
        }
        return true;
    }
}
