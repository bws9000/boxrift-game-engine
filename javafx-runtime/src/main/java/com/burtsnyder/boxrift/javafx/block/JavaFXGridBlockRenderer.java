package com.burtsnyder.boxrift.javafx.block;


import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.javafx.block.BlockRenderer;
import javafx.scene.Group;

public class JavaFXGridBlockRenderer {

    public static void render(Grid grid, Group layer, int blockSize) {
        layer.getChildren().clear();

        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                Block block = grid.peek(x, y);
                if (block == null) continue;

                layer.getChildren().add(
                        BlockRenderer.renderAt(
                                block,
                                blockSize,
                                x,
                                y
                        )
                );
            }
        }
    }
}

