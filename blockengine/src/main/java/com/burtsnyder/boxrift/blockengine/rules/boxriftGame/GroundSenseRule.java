package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;

public final class GroundSenseRule extends BaseRule {

    @Override
    public RuleDomainEnum domain() { return RuleDomainEnum.SENSE; }

    public GroundSenseRule(GameState state) { super(state); }

    @Override
    public int priority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        if (state.getActivePiece() == null) return;

        if (!state.canMoveActive(0, 1)) {
            ctx.currentFrame().setGravityBlocked(true);
        }
    }
}


