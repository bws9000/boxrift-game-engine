package com.burtsnyder.boxrift.javafx.block;

import com.burtsnyder.boxrift.blockengine.core.block.Block;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class BlockRenderer {
    // todo:
    public static Rectangle render(Block block, int blockSize) {
        Rectangle rect = new Rectangle(blockSize, blockSize);
        rect.setX(block.getPosition().x() * blockSize);
        rect.setY(block.getPosition().y() * blockSize);
        applyColor(rect, block);
        return rect;
    }

    // render a block at origin + offset
    public static Rectangle renderAt(Block block, int blockSize, int originX, int originY) {
        int gx = originX + block.getPosition().x();
        int gy = originY + block.getPosition().y();
        Rectangle rect = new Rectangle(blockSize, blockSize);
        rect.setX(gx * blockSize);
        rect.setY(gy * blockSize);
        applyColor(rect, block);
        return rect;
    }

    private static void applyColor(Rectangle rect, Block block) {
        switch (block.getColor()) {
            case CYAN -> rect.setFill(Color.CYAN);
            case YELLOW -> rect.setFill(Color.YELLOW);
            case PURPLE -> rect.setFill(Color.PURPLE);
            case GREEN -> rect.setFill(Color.GREEN);
            case ORANGE -> rect.setFill(Color.ORANGE);
            case BLUE -> rect.setFill(Color.BLUE);
            case RED -> rect.setFill(Color.RED);
        }
    }
}
