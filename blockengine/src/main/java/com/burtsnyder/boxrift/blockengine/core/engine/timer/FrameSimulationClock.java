package com.burtsnyder.boxrift.blockengine.core.engine.timer;


import com.burtsnyder.boxrift.blockengine.core.engine.FrameState;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.input.FrameInput;
import com.burtsnyder.boxrift.blockengine.core.rules.RuleScheduler;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;

public final class FrameSimulationClock implements SimulationClock {

    private final GameState state;
    private final RuleScheduler scheduler;
    private final RuleContext frameCtx;

    private FrameState currentFrame = new FrameState();

    public FrameState getCurrentFrame() {
        return currentFrame;
    }

    public FrameSimulationClock(
            GameState state,
            RuleScheduler scheduler,
            FrameInput frameInput
    ) {
        this.state = state;
        this.scheduler = scheduler;
        this.frameCtx = new RuleContext(frameInput);
    }

    @Override
    public void step() {

        FrameState previousFrame = currentFrame;
        currentFrame = new FrameState();

        frameCtx.beginFrame(currentFrame, previousFrame);

        scheduler.run(state, frameCtx);

        state.tickMutations();
        frameCtx.reset();
    }
}

