package com.burtsnyder.blockengine.core.rules;

import com.burtsnyder.blockengine.core.engine.GameState;
import com.burtsnyder.blockengine.core.rules.interfaces.Rule;

public abstract class BaseRule implements Rule {
    protected final GameState state;
    protected final RuleContext ruleContext;

    public BaseRule(GameState state) {
        this.ruleContext = null;
        this.state = state;
    }

    @Override
    public abstract void apply(GameState state, RuleContext ctx);
}

