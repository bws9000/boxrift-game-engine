package com.burtsnyder.boxrift.blockengine.core.rules;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.TransitionPhase;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.LockKey;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy.LockKeyPolicy;

public final class LockEligibilityRule extends BaseRule {

    public LockEligibilityRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.ENGINE;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        var piece = state.getActivePiece();
        if (piece == null) return;

        var key = LockKey.from(piece);
        boolean grounded = !state.canMoveActive(0, 1);

        if (!grounded) {
            state.lockGate().reset(key);
            return;
        }

        // touch every tick
        state.lockGate().arm(key);
        //e set delay once
        if (state.lockGate().phase(key) == TransitionPhase.ARMED) {
            state.lockGate().delay(key, LockKeyPolicy.lockDelayTicks(state.gameMode()));
        }
    }





}

