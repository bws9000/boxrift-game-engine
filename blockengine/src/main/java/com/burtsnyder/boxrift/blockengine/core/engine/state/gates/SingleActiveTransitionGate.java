package com.burtsnyder.boxrift.blockengine.core.engine.state.gates;

public final class SingleActiveTransitionGate<K extends TransitionKey>
        implements TransitionGate<K> {

    private K activeKey;
    private TransitionState<K> activeState;

    // eligibility was declared this tick
    private boolean touchedThisTick = false;

    private TransitionState<K> requireState(K key) {
        if (activeKey == null || !activeKey.equals(key)) {
            activeKey = key;
            activeState = new TransitionState<>(key);
        }
        return activeState;
    }

    @Override
    public void arm(K key) {
        touchedThisTick = true;
        requireState(key).arm();
    }

    @Override
    public void delay(K key, int ticks) {
        touchedThisTick = true;
        requireState(key).delay(ticks);
    }

    @Override
    public boolean isReady(K key) {
        return activeKey != null
                && activeKey.equals(key)
                && activeState != null
                && activeState.phase() == TransitionPhase.READY;
    }

    @Override
    public boolean consumeIfReady(K key) {
        if (!isReady(key)) return false;

        boolean consumed = activeState.consume();
        if (consumed) {
            activeKey = null;
            activeState = null;
        }
        return consumed;
    }

    @Override
    public TransitionPhase phase(K key) {
        if (activeKey == null || !activeKey.equals(key) || activeState == null) {
            return TransitionPhase.DISARMED;
        }
        return activeState.phase();
    }

    @Override
    public void tick() {
        if (!touchedThisTick) {
            resetAll();
            return;
        }

        touchedThisTick = false;

        if (activeState != null) {
            activeState.tick();
        }
    }

    private void resetAll() {
        activeKey = null;
        activeState = null;
    }

    @Override
    public void reset(K key) {
        if (activeKey != null && activeKey.equals(key)) {
            resetAll();
        }
    }
}

