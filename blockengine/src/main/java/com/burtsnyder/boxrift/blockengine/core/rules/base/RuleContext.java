package com.burtsnyder.boxrift.blockengine.core.rules.base;

import com.burtsnyder.boxrift.blockengine.core.engine.FrameState;
import com.burtsnyder.boxrift.blockengine.core.input.FrameInput;

import java.util.EnumSet;

public final class RuleContext {

    public enum Inhibition { GRAVITY }

    private final EnumSet<Inhibition> inhibitions =
            EnumSet.noneOf(Inhibition.class);

    private final FrameInput input;
    private FrameState currentFrame;
    private FrameState previousFrame;


    private boolean playerIntentThisTick = false;
    private boolean lateralWhileGrounded = false;

    public RuleContext(FrameInput input) {
        this.input = input;
    }

    public FrameInput input() {
        return input;
    }



    public void inhibit(Inhibition i) {
        inhibitions.add(i);
    }

    public boolean isInhibited(Inhibition i) {
        return inhibitions.contains(i);
    }





    public void markPlayerIntent() {
        playerIntentThisTick = true;
    }

/*    public boolean hasPlayerIntentThisTick() {
        return playerIntentThisTick;
    }*/

    public void markLateralWhileGrounded() {
        lateralWhileGrounded = true;
    }

/*    public boolean lateralWhileGrounded() {
        return lateralWhileGrounded;
    }*/





    public void beginFrame(FrameState current, FrameState previous) {
        this.currentFrame = current;
        this.previousFrame = previous;
    }

    public FrameState currentFrame() {
        return currentFrame;
    }

    public FrameState previousFrame() {
        return previousFrame;
    }





    public void reset() {
        inhibitions.clear();
        playerIntentThisTick = false;
        lateralWhileGrounded = false;
        input.clear();
    }
}


