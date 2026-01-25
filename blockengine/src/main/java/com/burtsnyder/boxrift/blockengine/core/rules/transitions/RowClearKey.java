package com.burtsnyder.boxrift.blockengine.core.rules.transitions;

import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.TransitionKey;

public record RowClearKey(int row) implements TransitionKey { }

