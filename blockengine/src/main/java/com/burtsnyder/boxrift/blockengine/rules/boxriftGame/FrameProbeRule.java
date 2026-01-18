package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.FrameState;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.rules.interfaces.Rule;

// for testing
public class FrameProbeRule extends BaseRule {

    public FrameState seenCurrent;
    public FrameState seenPrevious;
    public int runCount = 0;

    public FrameProbeRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.ENGINE;
    }

    @Override
    public int priority() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {
        runCount++;
        seenCurrent = ctx.currentFrame();
        seenPrevious = ctx.previousFrame();
    }
}



