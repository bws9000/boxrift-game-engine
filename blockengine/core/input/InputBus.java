package com.burtsnyder.blockengine.core.input;

/**
 * A minimal, logical message channel between input producers.
 * <p>
 * No built-in timing, throttling, or arbitration. The "bus" is
 * responsible only for queuing and delivering {@link InputSignal} objects.
 * {@link com.burtsnyder.blockengine.core.input.keyboard.KeyboardInputSystem}
 * handles DAS/ARR (delayed auto shift / auto repeat rate ->
 * see <a href="https://tetris.fandom.com/wiki/DAS">Tetris Wiki</a> or
 * <a href="https://harddrop.com/wiki/DAS">Hard Drop Wiki</a> for details).
 */
public interface InputBus {
    void emit(InputSignal signal);
    InputSignal poll();
}

