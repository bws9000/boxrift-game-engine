package com.burtsnyder.boxrift.blockengine.core.engine;

import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.input.InputBus;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameRenderer;

/**
 * Abstract class representing the main game loop.
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

    public GameLoop(int blockSize, int col, int row, InputBus inputBus, Grid grid) {
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
