package com.burtsnyder.boxrift.javafx.block;

import com.burtsnyder.boxrift.blockengine.core.block.Block;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class BlockRenderer {

    public static Rectangle renderGridCell(
            int gridX,
            int gridY,
            int blockSize
    ) {
        Rectangle rect = new Rectangle(blockSize, blockSize);
        rect.setX(gridX * blockSize);
        rect.setY(gridY * blockSize);
        return rect;
    }

/*    public static Rectangle renderColored(
            int gridX,
            int gridY,
            int blockSize,
            Block block
    ) {
        return renderAt(block, blockSize, gridX, gridY);
    }*/

    public static Rectangle renderAt(
            Block block,
            int blockSize,
            int gridX,
            int gridY
    ) {
        Rectangle rect = renderGridCell(gridX, gridY, blockSize);
        applyColor(rect, block);
        return rect;
    }


    private static void applyColor(Rectangle rect, Block block) {
        switch (block.color()) {
            case CYAN -> rect.setFill(javafx.scene.paint.Color.CYAN);
            case YELLOW -> rect.setFill(javafx.scene.paint.Color.YELLOW);
            case PURPLE -> rect.setFill(javafx.scene.paint.Color.PURPLE);
            case GREEN -> rect.setFill(javafx.scene.paint.Color.GREEN);
            case ORANGE -> rect.setFill(javafx.scene.paint.Color.ORANGE);
            case BLUE -> rect.setFill(javafx.scene.paint.Color.BLUE);
            case RED -> rect.setFill(javafx.scene.paint.Color.RED);
        }
    }
}

