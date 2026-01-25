package com.burtsnyder.boxrift.blockengine.core.engine.state.gates;

public interface TransitionGate<K extends TransitionKey> {

    void arm(K key);

    void delay(K key, int ticks);

    boolean isReady(K key);

    boolean consumeIfReady(K key);

    TransitionPhase phase(K key);

    void tick();

    void reset(K key);
}

