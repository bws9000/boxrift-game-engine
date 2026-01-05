package com.burtsnyder.boxrift.blockengine.core.input.keyboard;

import com.burtsnyder.boxrift.blockengine.core.input.*;

import java.util.ArrayList;
import java.util.List;

public class KeyboardInputSystem {
    private final InputBus inputBus;
    private final long das;
    private final long arr;

    private InputAction heldMove = null;
    private boolean initialDelayDone = false;
    private long firstHeldAt = 0;
    private long lastRepeatAt = 0;

    public KeyboardInputSystem(InputBus inputBus, long dasMs, long arrMs) {
        this.inputBus = inputBus;
        this.das = dasMs * 1_000_000L;
        this.arr = arrMs * 1_000_000L;
    }

    /**
     * Processes pending {@link InputSignal}s and applies DAS/ARR timing
     * to generate the actions for this frame.
     * <p>
     * - ONE_SHOT -- fire immediately
     * - PRESS/RELEASE -- track holdable actions
     * - Held action  --> fire once, then repeat after DAS delay and every ARR interval
     * @param now current time nanoseconds
     * @return actions to execute for this frame
     */
    public List<InputAction> update(long now) {
        List<InputAction> out = new ArrayList<>();

        for (InputSignal s; (s = inputBus.poll()) != null; ) {
            switch (s.kind()) {
                case ONE_SHOT -> out.add(s.action());
                case PRESS -> onPress(s.action(), now);
                case RELEASE -> onRelease(s.action());
            }
        }

        // DAS ARR
        if (heldMove != null) {
            if (!initialDelayDone) {
                // keypress triggered
                out.add(heldMove);
                initialDelayDone = true;
                lastRepeatAt = now;
            } else {
                // Holding key down
                if (now - firstHeldAt >= das) {
                    if (now - lastRepeatAt >= arr) {
                        out.add(heldMove);
                        lastRepeatAt = now;
                    }
                }
            }
        }
        return out;
    }

    private void onPress(InputAction action, long now) {
        switch (action) {
            case MOVE_LEFT, MOVE_RIGHT, SOFT_DOWN, ROTATE_LEFT, ROTATE_RIGHT -> {
                heldMove = action;
                initialDelayDone = false;
                firstHeldAt = now;
            }
            default -> {}
        }
    }

    private void onRelease(InputAction action) {
        if (heldMove == action) {
            heldMove = null;
            initialDelayDone = false;
        }
    }
}
