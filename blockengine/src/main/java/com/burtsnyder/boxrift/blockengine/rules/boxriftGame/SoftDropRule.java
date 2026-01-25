
package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.rules.interfaces.PlayerIntentMovementRule;

import static com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext.Inhibition.GRAVITY;

public class SoftDropRule extends BaseRule implements PlayerIntentMovementRule {
    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.INTENT;
    }

    public SoftDropRule(GameState state) {
        super(state);
    }

    @Override public int priority() { return 25; }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        boolean dropped = false;
        while (ctx.input().consumeIf(a -> a == InputAction.SOFT_DOWN)) {
            if (tryDown(state)) dropped = true;
            else break;
        }
        if (dropped) ctx.inhibit(GRAVITY);
    }

    private boolean tryDown(GameState state) {
        if (!state.canMoveActive(0, 1)) return false;
        state.setActivePiece(state.getActivePiece().move(0, 1));
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
