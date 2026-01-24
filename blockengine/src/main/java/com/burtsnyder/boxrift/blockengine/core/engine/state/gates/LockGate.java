package com.burtsnyder.boxrift.blockengine.core.engine.state.gates;


public final class LockGate implements Gate {

    private final long delayMillis;
    private long armedAt = -1;
    private boolean consumed = false;

    public LockGate(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    /** returns true exactly once when delay expires */
    @Override
    public boolean tryOpen() {
        if (armedAt < 0 || consumed) return false;

        if (System.currentTimeMillis() - armedAt >= delayMillis) {
            consumed = true;
            return true;
        }

        return false;
    }

    @Override
    public void arm() {
        if (armedAt < 0) {
            armedAt = System.currentTimeMillis();
            consumed = false;
        }
    }

    @Override
    public void reset() {
        armedAt = -1;
        consumed = false;
    }
}

/*public final class LockGate implements Gate {

    private final long delayMillis;
    private long armedAt = -1;

    public LockGate(long delayMillis) {
        this.delayMillis = delayMillis;
    }

*//*    @Override
    public boolean isOpen() {
        if (armedAt < 0) {
            return false;
        }
        return System.currentTimeMillis() - armedAt >= delayMillis;
    }*//*

    @Override
    public boolean tryOpen() {
        return false;
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
}*/

