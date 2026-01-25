package com.burtsnyder.boxrift.javafx.block;

import com.burtsnyder.boxrift.blockengine.core.block.BlockSetColor;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy.BlinkPolicy;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy.ToggleBlinkPolicy;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class JavaFXGridBlockRenderer {
    private static final BlinkPolicy blinkPolicy = new ToggleBlinkPolicy(6);


    public static void render(GameState state, Group layer, int blockSize) {
        layer.getChildren().clear();

        int rows = state.getGrid().getHeight();

        for (int y = 0; y < rows; y++) {

            long blinkStart =
                    state.getBlinkStartFrame(y).orElse(state.tick);

            boolean visible =
                    blinkPolicy.isVisible(state.tick, blinkStart);

            for (int x = 0; x < state.getGrid().getWidth(); x++) {
                var block = state.getGrid().peek(x, y);
                if (block == null) continue;


                if (block.getMetadata().pendingClear() && !visible) {
                    continue;
                }

                Rectangle r = new Rectangle(
                        x * blockSize,
                        y * blockSize,
                        blockSize,
                        blockSize
                );

                r.setFill(toFxColor(block.color()));
                layer.getChildren().add(r);
            }
        }
    }



    private static Color toFxColor(BlockSetColor c) {
        return switch (c) {
            case CYAN   -> Color.CYAN;
            case YELLOW -> Color.YELLOW;
            case PURPLE -> Color.PURPLE;
            case GREEN  -> Color.GREEN;
            case ORANGE -> Color.ORANGE;
            case RED    -> Color.RED;
            case BLUE   -> Color.BLUE;
        };
    }
}


