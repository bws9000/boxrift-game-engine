package com.burtsnyder.boxrift.blockengine.core.rules.interfaces;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;

public interface PlayerIntentMovementRule {

    long lastIntentTick();
    void onPlayerIntent(GameState state);

}


