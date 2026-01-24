package com.burtsnyder.boxrift.javafx.block;

import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.block.BlockSetColor;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * javaFX renderer must explicitly derive color from Block state every frame.
 *
 * Unlike libGDX (immediate-mode rendering), javaFX uses a retained scene graph.
 * We fully rebuild the grid layer each tick, so visual properties like color
 * must be recomputed from engine state instead of relying on previously drawn nodes.
 *
 * BlockSetColor is an engine-level identity (UI-agnostic). This mapping converts
 * that identity into a javaFX Color without leaking javaFX types into the core engine.
 *
 * ...made this comment when I only tested two renderers javaFX and libGDX
 */
public class JavaFXGridBlockRenderer {

    public static void render(Grid grid, Group layer, int blockSize) {
        layer.getChildren().clear();

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                var block = grid.peek(x, y);
                if (block == null) continue;

                Rectangle r = new Rectangle(
                        x * blockSize,
                        y * blockSize,
                        blockSize,
                        blockSize
                );



                Color fxColor = toFxColor(block.color());
                r.setFill(fxColor);
                r.setOpacity(block.getMetadata().isBlink() ? 0.2 : 1.0);


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


