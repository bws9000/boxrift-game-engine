package com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy;

public record ToggleBlinkPolicy(int periodTicks) implements BlinkPolicy {

    @Override
    public boolean isVisible(long tick, long blinkStartTick) {
        long elapsed = tick - blinkStartTick;
        return (elapsed / periodTicks) % 2 == 0;
    }
}

