package com.burtsnyder.boxrift.blockengine.core.engine.state.gates;


public final class LockGate implements Gate {

    private final long delayMillis;
    private long armedAt = -1;

    public LockGate(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    @Override
    public boolean isOpen() {
        if (armedAt < 0) {
            return false;
        }
        return System.currentTimeMillis() - armedAt >= delayMillis;
    }

    @Override
    public void arm() {
        if (armedAt < 0) {
            armedAt = System.currentTimeMillis();
        }
    }

    @Override
    public void reset() {
        armedAt = -1;
    }
}

