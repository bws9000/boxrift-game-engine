package com.burtsnyder.boxrift.libgdxcore.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.input.InputBus;
import com.burtsnyder.boxrift.blockengine.core.input.InputSignal;

public final class LibGDXKeyboardAdapter extends InputAdapter {
    private final InputBus inputBus;

    public LibGDXKeyboardAdapter(InputBus inputBus) {
        this.inputBus = inputBus;
    }

    public static void attach(InputBus inputBus) {
        Gdx.input.setInputProcessor(new LibGDXKeyboardAdapter(inputBus));
    }

    @Override
    public boolean keyDown(int keycode) {
        InputAction action = mapKey(keycode);
        if (action != null) {
            inputBus.emit(InputSignal.press(action));
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        InputAction action = mapKey(keycode);
        if (action != null) {
            inputBus.emit(InputSignal.release(action));
            return true;
        }
        return false;
    }

    private InputAction mapKey(int keycode) {
        return switch (keycode) {
            case Input.Keys.LEFT  -> InputAction.MOVE_LEFT;
            case Input.Keys.RIGHT -> InputAction.MOVE_RIGHT;
            case Input.Keys.DOWN  -> InputAction.SOFT_DOWN;
            case Input.Keys.UP    -> InputAction.MOVE_UP;
            default -> null;
        };
    }
}




