package com.burtsnyder.boxrift.blockengine.core.engine.state.mutations;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;

interface GameMutation {
    void apply(GameState state);
}

