package com.burtsnyder.boxrift.javafx.board;

import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.renderer.GridRenderCell;
import com.burtsnyder.boxrift.blockengine.core.renderer.GridRenderLogic;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public final class JavaFXGridLinesRenderer {

    public static void render(Grid grid, Group layer, int blockSize) {
        layer.getChildren().clear();

        for (GridRenderCell cell : GridRenderLogic.generate(grid, blockSize)) {
            Rectangle rect = new Rectangle(
                    cell.pixelX(),
                    cell.pixelY(),
                    cell.width(),
                    cell.height()
            );
            rect.setFill(Color.TRANSPARENT);
            rect.setStroke(Color.GRAY);
            rect.setStrokeWidth(0.5);
            layer.getChildren().add(rect);
        }
    }
}

