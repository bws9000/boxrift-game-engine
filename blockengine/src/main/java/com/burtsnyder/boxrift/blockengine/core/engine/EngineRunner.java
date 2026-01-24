package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.input.InputBus;
import com.burtsnyder.boxrift.blockengine.core.input.keyboard.KeyboardInputSystem;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EngineRunner {
    private static final Logger log =
            LoggerFactory.getLogger(EngineRunner.class);

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
        log.info("Boxrift logging active");
    }

    public GameManager getManager() {
        return manager;
    }


    public void step(long now) {
        var actions = keyboard.update(now);
        manager.enqueueActions(actions);
        manager.tick();

        if (renderer != null) {
            renderer.render(manager.getState());
        }
    }
}
