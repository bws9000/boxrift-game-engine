package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.rules.interfaces.PlayerIntentMovementRule;

public class LateralMoveRule extends BaseRule {

    public LateralMoveRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.INTENT;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {

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

        if (movedAny) {
            ctx.inhibit(RuleContext.Inhibition.GRAVITY);
        }
    }

    private boolean tryMove(GameState state, int dx) {

        var p = state.getActivePiece();
        if (p == null) return false;

        if (!state.canMoveHorizontally(dx)) return false;

        state.setActivePiece(p.move(dx, 0));
        return true;
    }


}
