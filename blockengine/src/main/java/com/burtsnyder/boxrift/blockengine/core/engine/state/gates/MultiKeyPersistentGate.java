package com.burtsnyder.boxrift.blockengine.core.engine.state.gates;

import java.util.HashMap;
import java.util.Map;

public final class MultiKeyPersistentGate<K extends TransitionKey>
        implements TransitionGate<K> {

    private final Map<K, TransitionState<K>> states = new HashMap<>();

    @Override
    public void arm(K key) {
        states.computeIfAbsent(key, TransitionState::new).arm();
    }

    @Override
    public void delay(K key, int ticks) {
        states.computeIfAbsent(key, TransitionState::new).delay(ticks);
    }

    @Override
    public boolean isReady(K key) {
        TransitionState<K> s = states.get(key);
        return s != null && s.phase() == TransitionPhase.READY;
    }

    @Override
    public boolean consumeIfReady(K key) {
        TransitionState<K> s = states.get(key);
        if (s == null || s.phase() != TransitionPhase.READY) {
            return false;
        }

        boolean consumed = s.consume();
        if (consumed) {
            states.remove(key);
        }
        return consumed;
    }

    @Override
    public TransitionPhase phase(K key) {
        TransitionState<K> s = states.get(key);
        return s == null ? TransitionPhase.DISARMED : s.phase();
    }

    @Override
    public void tick() {
        for (TransitionState<K> s : states.values()) {
            s.tick();
        }
    }

    @Override
    public void reset(K key) {
        states.remove(key);
    }
}

