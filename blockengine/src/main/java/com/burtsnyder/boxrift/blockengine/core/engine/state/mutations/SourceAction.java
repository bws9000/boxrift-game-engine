package com.burtsnyder.boxrift.blockengine.core.engine.state.mutations;

public interface SourceAction {

    /**
     * called when the gate is armed (first tick)
     */
    void onArm();

    /**
     * called every tick while gate is closed
     */
    void onTick();

    /**
     * called when the gate opens (just before commit)
     */
    void onRelease();

    /**
     * called after commit to clean up state
     */
    void reset();
}

