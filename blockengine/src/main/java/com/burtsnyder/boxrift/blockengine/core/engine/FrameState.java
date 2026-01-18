package com.burtsnyder.boxrift.blockengine.core.engine;


public final class FrameState {

    private boolean gravityBlocked;

    public boolean gravityBlocked() {
        return gravityBlocked;
    }

    public void setGravityBlocked(boolean gravityBlocked) {
        this.gravityBlocked = gravityBlocked;
    }
}

