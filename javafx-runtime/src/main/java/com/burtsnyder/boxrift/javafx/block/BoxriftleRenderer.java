package com.burtsnyder.boxrift.javafx.block;

import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;
import com.burtsnyder.boxrift.blockengine.core.block.Block;
import javafx.scene.Group;

public class BoxriftleRenderer {

    public static Group render(Boxriftle piece, int blockSize, int gridRows) {
        Group group = new Group();
        var o = piece.getOrigin();

        for (Block block : piece.getBlocks()) {
            int gx = o.x() + block.position().x();
            int gy = o.y() + block.position().y();

            // javaFX coordinate inversion
            //int invertedY = (gridRows - 1) - gy;

            //reversed compared to libgdx
            group.getChildren().add(
                    BlockRenderer.renderAt(block, blockSize, gx, gy)
            );

            /*group.getChildren().add(
                    BlockRenderer.renderAt(
                            block,
                            blockSize,
                            gx,
                            invertedY
                    )
            );*/
        }
        return group;
    }
}

