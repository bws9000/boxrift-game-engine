package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.input.InputBus;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameRenderer;

/**
 * Abstract class representing the main game loop.
 * <p>This class provides a base structure for implementing platform-specific game loops
 * (e.g., JavaFX, LibGDX, etc.). It encapsulates a {@link GameManager} that handles the core
 * game logic and state updates, while delegating rendering to a {@link GameRenderer} implementation.
 * <p>Subclasses must implement the {@link #start()} method to define how and when
 * the game loop executes (e.g., using a timer or a render thread).
 * <p>Rendering is decoupled from logic via {@link #setRenderer(GameRenderer)} and {@link #updateView()},
 * enabling the engine to be agnostic of specific rendering technologies.
 *
 * @see GameManager
 * @see GameRenderer
 */
public abstract class GameLoop {
    protected final InputBus inputBus;
    protected final int blockSize;
    protected final int col;
    protected final int row;
    protected final GameManager manager;
    protected GameRenderer renderer;

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

    public void updateView() {
        if (renderer != null) {
            renderer.render(manager.getState());
        }
    }

    public GameLoop(int blockSize, int col, int row, InputBus inputBus) {
        this.blockSize = blockSize;
        this.col = col;
        this.row = row;
        this.inputBus = inputBus;
        this.manager = new GameManager(col, row);
    }

    public GameManager getManager() {
        return manager;
    }

    public abstract void start();

}
