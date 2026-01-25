package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.TransitionPhase;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.VoidKey;

public final class SpawnEligibilityRule extends BaseRule {

    private static final VoidKey KEY = VoidKey.INSTANCE;

    public SpawnEligibilityRule(GameState state) {
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
        if (state.getActivePiece() != null) {
            return;
        }

        // arm once per (the >no piece< zone)
        if (state.spawnGate().phase(KEY) == TransitionPhase.DISARMED) {
            state.spawnGate().arm(KEY);
        }
    }

}

