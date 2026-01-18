package com.burtsnyder.boxrift.blockengine.core.rules.base;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.interfaces.PlayerIntentMovementRule;

public abstract class AbstractPlayerIntentMovementRule
        extends BaseRule
        implements PlayerIntentMovementRule {

    protected AbstractPlayerIntentMovementRule(GameState state) {
        super(state);
    }

/*    //subclass helper
    protected final void markPlayerIntent(GameState state) {
        state.markPlayerIntentThisTick();
        onPlayerIntent(state);
    }*/
}


