
package com.burtsnyder.blockengine.core.rules.interfaces;

import com.burtsnyder.blockengine.core.engine.GameState;
import com.burtsnyder.blockengine.core.rules.RuleContext;

public interface Rule {
    int priority(); // lower runs first
    void apply(GameState state, RuleContext ctx);

    // for 1-arg method possibly
    default void apply(GameState state) { apply(state, null); }
}



