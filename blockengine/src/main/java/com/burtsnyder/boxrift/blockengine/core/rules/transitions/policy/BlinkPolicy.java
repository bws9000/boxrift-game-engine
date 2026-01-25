package com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy;

public interface BlinkPolicy {
    boolean isVisible(long tick, long blinkStartTick);
}

