package com.burtsnyder.boxrift.blockengine.core.rules;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class RuleScheduler {

    private final Map<RuleDomainEnum, List<BaseRule>> rulesByDomain =
            new EnumMap<>(RuleDomainEnum.class);

    public RuleScheduler() {
        for (RuleDomainEnum d : RuleDomainEnum.values()) {
            rulesByDomain.put(d, new ArrayList<>());
        }
    }

    public void addRule(BaseRule rule) {
        rulesByDomain.get(rule.domain()).add(rule);
    }

    public void run(GameState state, RuleContext ctx) {
        runDomain(RuleDomainEnum.SENSE, state, ctx);
        runDomain(RuleDomainEnum.INTENT, state, ctx);
        runDomain(RuleDomainEnum.SIMULATION, state, ctx);

        runDomain(RuleDomainEnum.ENGINE, state, ctx);

        state.tickTransitions(); // step into transitions

        runDomain(RuleDomainEnum.RESOLUTION, state, ctx);
        runDomain(RuleDomainEnum.MUTATION, state, ctx);
    }



    private void runDomain(RuleDomainEnum domain, GameState state, RuleContext ctx) {
        for (BaseRule r : rulesByDomain.get(domain)) {
            if (r.isEligible(state, ctx)) {
                r.apply(state, ctx);
            }
        }
    }
}




