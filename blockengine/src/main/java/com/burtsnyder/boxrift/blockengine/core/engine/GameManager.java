package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.engine.timer.FrameSimulationClock;
import com.burtsnyder.boxrift.blockengine.core.engine.timer.SimulationClock;
import com.burtsnyder.boxrift.blockengine.core.input.FrameInput;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.RuleScheduler;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameEngine;


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
