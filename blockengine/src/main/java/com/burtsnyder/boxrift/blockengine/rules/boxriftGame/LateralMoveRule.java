package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.base.AbstractPlayerIntentMovementRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import static com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext.Inhibition.GRAVITY;

public class LateralMoveRule extends AbstractPlayerIntentMovementRule {

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.INTENT;
    }

    public LateralMoveRule(GameState state) {
        super(state);
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        if (!state.canMoveActive(0, 1)) {
            ctx.markLateralWhileGrounded();
        }

        boolean movedAny = false;

        while (true) {
            boolean moved =
                    ctx.input().consumeIf(a -> a == InputAction.MOVE_LEFT)
                            && tryMove(state, -1)
                            || ctx.input().consumeIf(a -> a == InputAction.MOVE_RIGHT)
                            && tryMove(state, 1);

            if (!moved) break;
            movedAny = true;
        }

        if (movedAny && !state.canMoveActive(0, 1)) {
            ctx.markLateralWhileGrounded();
        }

        if (movedAny) {
            ctx.markPlayerIntent();
            ctx.inhibit(GRAVITY);
        }
    }


    private boolean tryMove(GameState state, int dx) {

        var p = state.getActivePiece();
        if (p == null) {
            return false;
        }

        boolean can;
        // lateral move-- ignore downward collision
        can = state.canMoveHorizontally(dx);

        if (!can) return false;// doesn't dissapear into the sides

        var next = p.move(dx, 0);
        state.setActivePiece(next);

        return true;
    }



    @Override
    public long lastIntentTick() {
        return 0;
    }

    @Override
    public void onPlayerIntent(GameState state) {

    }
}
