package com.burtsnyder.boxrift.javafx;

import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.TransitionPhase;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.RowClearKey;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JavaFXGridRenderer {

    public static void render(GameState state, Group layer, int blockSize) {
        Grid grid = state.getGrid();
        int rows = grid.getHeight();

        for (int y = 0; y < rows; y++) {
            RowClearKey key = new RowClearKey(y);
            boolean blinking =
                    state.rowClearGate().phase(key) == TransitionPhase.DELAYING;

            for (int x = 0; x < grid.getWidth(); x++) {
                var block = grid.peek(x, y);
                if (block == null) continue;

                Rectangle rect = new Rectangle(
                        x * blockSize,
                        (rows - 1 - y) * blockSize,
                        blockSize,
                        blockSize
                );

                if (blinking) {
                    rect.setFill(Color.WHITE);
                } else {
                    rect.setFill(resolveColor(block));
                }

                layer.getChildren().add(rect);
            }
        }
    }

    private static Color resolveColor(Block block) {
        return switch (block.color()) {
            case CYAN   -> Color.CYAN;
            case YELLOW -> Color.YELLOW;
            case PURPLE -> Color.PURPLE;
            case GREEN  -> Color.GREEN;
            case ORANGE -> Color.ORANGE;
            case BLUE   -> Color.BLUE;
            case RED    -> Color.RED;
        };
    }
}
