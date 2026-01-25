package com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy;

import com.burtsnyder.boxrift.blockengine.core.engine.state.GameMode;

public final class RowClearPolicy {
    //public static final int BLINK_TICKS = 60;
    public static int blinkTicks(GameMode mode) {
        return switch(mode){
            case CLASSIC -> 90;
            case SUPER_DUPER -> 160;
        };
    }
}
