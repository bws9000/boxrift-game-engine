package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.input.FrameInput;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.interfaces.Rule;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameEngine;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameManager implements GameEngine {
    private final GameState state;
    private final List<Rule> rules = new ArrayList<>();
    private final FrameInput frameInput = new FrameInput();
    private final RuleContext frameCtx = new RuleContext(frameInput);

    public GameManager(int col, int row) {
        this.state = new GameState(col, row);
    }

    public GameState getState() { return state; }

    public void addRule(Rule rule) {
        rules.add(rule);
        rules.sort(Comparator.comparingInt(Rule::priority));
    }

    public void enqueueActions(Iterable<InputAction> actions) {
        frameInput.enqueue(actions);
    }

    public void applyRules() {
        for (Rule rule : rules) {
            rule.apply(state, frameCtx);
        }
        frameCtx.reset();
    }



    @Override
    public void tick() {
        applyRules();
    }

/*    public void applyInput(InputAction a) {
        frameInput.enqueue(List.of(a));
    }*/
}