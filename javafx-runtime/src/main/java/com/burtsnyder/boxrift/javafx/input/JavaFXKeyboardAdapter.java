package com.burtsnyder.boxrift.javafx.input;

import com.burtsnyder.boxrift.blockengine.core.input.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.*;

/**
 * Maps JavaFX {@link KeyCode} events to engine-level {@link InputSignal}s
 * and delivers them to an {@link InputBus}.
 * <p>
 * Acts as a thin UI adapter: no game logic or auto-repeat and delayed shift timing (handled by
 * {@link com.burtsnyder.blockengine.core.input.keyboard.KeyboardInputSystem}).
 * <p>
 * <ul>
 *   <li>Bind key presses/releases to {@link InputAction}s.</li>
 *   <li>Emit {@code PRESS}, {@code RELEASE}, or {@code ONE_SHOT} signals.</li>
 *   <li>Filter OS key repeats (only one PRESS per physical press).</li>
 *   <li>Clear pressed state when {@link Stage} loses focus.</li>
 * </ul>
 * <p>
 * Use {@link #attachDefault(Scene, Stage, InputBus)} for default bindings:
 * <ul>
 *   <li>left/Right/Down → HOLD actions</li>
 *   <li>Z/X → ONE_SHOT rotate actions</li>
 * </ul>
 */
public final class JavaFXKeyboardAdapter {
    private final InputBus inputBus;
    private final Map<KeyCode, InputAction> holdBindings;
    private final Map<KeyCode, InputAction> oneShotBindings;
    private final Set<KeyCode> pressed = new HashSet<>();

    public JavaFXKeyboardAdapter(InputBus inputBus,
                                 Map<KeyCode, InputAction> holdBindings,
                                 Map<KeyCode, InputAction> oneShotBindings) {
        this.inputBus = Objects.requireNonNull(inputBus);
        this.holdBindings = new HashMap<>(holdBindings);
        this.oneShotBindings = new HashMap<>(oneShotBindings);
    }

    public void attach(Scene scene, Stage stage) {
        stage.focusedProperty().addListener(
                (obs, was, is) -> {
                    if (!is) pressed.clear(); });

        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            if (!pressed.add(code)){
                return;// stop os repeats
            }
            InputAction a = holdBindings.get(code);
            if (a != null) { inputBus.emit(InputSignal.press(a)); return; }
            a = oneShotBindings.get(code);
            if (a != null) { inputBus.emit(InputSignal.oneShot(a)); }
        });

        scene.setOnKeyReleased(e -> {
            KeyCode code = e.getCode();
            pressed.remove(code);
            InputAction a = holdBindings.get(code);
            if (a != null) { inputBus.emit(InputSignal.release(a)); }
        });
    }

    public static void attachDefault(Scene scene, Stage stage, InputBus inputBus) {
        Map<KeyCode, InputAction> hold = Map.of(
                KeyCode.LEFT,  InputAction.MOVE_LEFT,
                KeyCode.RIGHT, InputAction.MOVE_RIGHT,
                KeyCode.DOWN,  InputAction.SOFT_DOWN
        );
        Map<KeyCode, InputAction> oneShot = Map.of(
                KeyCode.Z, InputAction.ROTATE_LEFT,
                KeyCode.X, InputAction.ROTATE_RIGHT
        );
        var adapter = new JavaFXKeyboardAdapter(inputBus, hold, oneShot);
        adapter.attach(scene, stage);
    }
}
