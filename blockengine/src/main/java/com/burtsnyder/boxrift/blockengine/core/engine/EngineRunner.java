package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.input.InputBus;
import com.burtsnyder.boxrift.blockengine.core.input.keyboard.KeyboardInputSystem;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameRenderer;

public final class EngineRunner {

    private final GameManager manager;
    private final KeyboardInputSystem keyboard;
    private final GameRenderer renderer;

    public EngineRunner(
            int blockSize,
            int col,
            int row,
            InputBus inputBus,
            GameRenderer renderer
    ) {
        this.manager = new GameManager(col, row);
        this.keyboard = new KeyboardInputSystem(inputBus, 170, 40);
        this.renderer = renderer;
    }

    public GameManager getManager() {
        return manager;
    }

/*    private long lastTickAt = 0;
    private static final long TICK_NANOS = 500_000_000L; // 0.5s*/

    /*public void step(long now) {
        if (now - lastTickAt >= TICK_NANOS) {
            var actions = keyboard.update(now);
            manager.enqueueActions(actions);
            manager.tick();

            if (renderer != null) {
                renderer.render(manager.getState());
                lastTickAt = now;
            }

        }
    }*/
    public void step(long now) {
        var actions = keyboard.update(now);
        manager.enqueueActions(actions);
        manager.tick();

        if (renderer != null) {
            renderer.render(manager.getState());
        }
    }
}
