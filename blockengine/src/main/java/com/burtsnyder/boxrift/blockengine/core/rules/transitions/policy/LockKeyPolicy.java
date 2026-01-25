package com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy;


import com.burtsnyder.boxrift.blockengine.core.engine.state.GameMode;

public final class LockKeyPolicy {
    public static int lockDelayTicks(GameMode mode) {
        return switch (mode) {
            case CLASSIC -> 30;
            case SUPER_DUPER -> 120;
        };
    }
}


