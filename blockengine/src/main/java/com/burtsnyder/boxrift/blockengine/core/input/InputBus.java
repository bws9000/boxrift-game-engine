package com.burtsnyder.boxrift.blockengine.core.input;

/**
 * A minimal, logical message channel between input producers.
 * see <a href="https://tetris.fandom.com/wiki/DAS">Tetris Wiki</a> or
 * <a href="https://harddrop.com/wiki/DAS">Hard Drop Wiki</a> for details).
 */
public interface InputBus {
    void emit(InputSignal signal);
    InputSignal poll();
}

