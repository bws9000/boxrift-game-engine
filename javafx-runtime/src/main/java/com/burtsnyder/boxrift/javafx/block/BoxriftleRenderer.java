package com.burtsnyder.boxrift.javafx.block;

import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;
import com.burtsnyder.boxrift.blockengine.core.block.Block;

import javafx.scene.Group;

public class BoxriftleRenderer {
    public static Group render(Boxriftle piece, int blockSize) {
        Group group = new Group();
        var o = piece.getOrigin();
        for (Block block : piece.getBlocks()) {
            group.getChildren().add(
                    BlockRenderer.renderAt(block, blockSize, o.x(), o.y()) // origin + offset
            );
        }
        return group;
    }
}
