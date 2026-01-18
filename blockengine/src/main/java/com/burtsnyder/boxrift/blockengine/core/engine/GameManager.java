package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.engine.timer.FrameSimulationClock;
import com.burtsnyder.boxrift.blockengine.core.engine.timer.SimulationClock;
import com.burtsnyder.boxrift.blockengine.core.input.FrameInput;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.RuleScheduler;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.interfaces.Rule;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameEngine;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class GameManager implements GameEngine {

    private final GameState state;
    private final RuleScheduler scheduler = new RuleScheduler();
    private final FrameInput frameInput = new FrameInput();
    private final SimulationClock clock;

    public GameManager(int col, int row) {
        this.state = new GameState(col, row);
        this.clock = new FrameSimulationClock(state, scheduler, frameInput);
    }

    public GameState getState() {
        return state;
    }

    public RuleScheduler getScheduler() {
        return scheduler;
    }

    public void enqueueActions(Iterable<InputAction> actions) {
        frameInput.enqueue(actions);
    }

    @Override
    public void tick() {
        clock.step();
    }
}




/*package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.input.FrameInput;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.RuleScheduler;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.interfaces.Rule;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameEngine;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameManager implements GameEngine {

    private final GameState state;
    private final FrameInput frameInput = new FrameInput();
    private final RuleContext frameCtx = new RuleContext(frameInput);
    private FrameState currentFrame  = new FrameState();
    private final RuleScheduler scheduler = new RuleScheduler();

    public GameManager(int col, int row) {
        this.state = new GameState(col, row);
    }

    public GameState getState() {
        return state;
    }

    public RuleScheduler getScheduler() {
        return scheduler;
    }

    public void enqueueActions(Iterable<InputAction> actions) {
        frameInput.enqueue(actions);
    }


        @Override
        public void tick() {
            FrameState previousFrame = currentFrame;
            currentFrame = new FrameState();

            frameCtx.beginFrame(currentFrame, previousFrame);
            scheduler.run(state, frameCtx);

            frameCtx.reset();
        }



}*/
