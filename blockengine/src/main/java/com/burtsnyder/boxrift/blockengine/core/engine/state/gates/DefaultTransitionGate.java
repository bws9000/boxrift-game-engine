package com.burtsnyder.boxrift.blockengine.core.engine.state.gates;

import java.util.HashMap;
import java.util.Map;

public final class DefaultTransitionGate<K extends TransitionKey>
        implements TransitionGate<K> {

    private K activeKey;
    private TransitionState<K> activeState;

    // did a rule arm the gate this tick....
    private boolean touchedThisTick = false;

    private TransitionState<K> requireState(K key) {
        if (activeKey == null || !activeKey.equals(key) || activeState == null) {
            activeKey = key;
            activeState = new TransitionState<>(key);
        }
        return activeState;
    }

    @Override
    public void arm(K key) {
        touchedThisTick = true;
        // if key changes create a fresh state and arm it
        TransitionState<K> s = requireState(key);
        // arming same key repeatedly --> idempotent
        s.arm();
    }

    @Override
    public void delay(K key, int ticks) {
        touchedThisTick = true;

        TransitionState<K> s = requireState(key);

        // delay is only meaningful once armed; transitionState should guards this
        s.delay(ticks);
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
            // consumed, this gate is done until re-armed
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
        // if nobody declared eligible this tick disarm completely
        if (!touchedThisTick) {
            reset();
            return;
        }

        touchedThisTick = false;

        if (activeState != null) {
            activeState.tick();
        }
    }


    public void reset() {
        activeKey = null;
        activeState = null;
    }


    @Override
    public void reset(K key) {
        if (activeKey != null && activeKey.equals(key)) {
            reset();
        }
    }
}
