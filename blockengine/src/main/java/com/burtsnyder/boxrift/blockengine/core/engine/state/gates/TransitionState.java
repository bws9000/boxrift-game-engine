package com.burtsnyder.boxrift.blockengine.core.engine.state.gates;

public final class TransitionState<K> {
    private double remainingSeconds;

    private final K key;
    private TransitionPhase phase = TransitionPhase.DISARMED;
    private int remainingTicks = 0;

    TransitionState(K key) {
        this.key = key;
    }

    public K key() { return key; }
    public TransitionPhase phase() { return phase; }
    public int remainingTicks() { return remainingTicks; }

    void arm() {
        if (phase == TransitionPhase.DISARMED) {
            phase = TransitionPhase.ARMED;
        }
    }

    void delay(int ticks) {
        if (phase == TransitionPhase.ARMED || phase == TransitionPhase.DELAYING) {
            remainingTicks = Math.max(remainingTicks, ticks);
            phase = TransitionPhase.DELAYING;
        }
    }

    void tick() {
        // ifarmed and no delay was requested, it becomes ready
        if (phase == TransitionPhase.ARMED) {
            phase = TransitionPhase.READY;
            return;
        }

        if (phase == TransitionPhase.DELAYING) {
            remainingTicks--;
            if (remainingTicks <= 0) {
                phase = TransitionPhase.READY;
            }
        }
    }

    boolean consume() {
        if (phase == TransitionPhase.READY) {
            phase = TransitionPhase.CONSUMED;
            return true;
        }
        return false;
    }

    void reset() {
        phase = TransitionPhase.DISARMED;
        remainingTicks = 0;
    }
}

