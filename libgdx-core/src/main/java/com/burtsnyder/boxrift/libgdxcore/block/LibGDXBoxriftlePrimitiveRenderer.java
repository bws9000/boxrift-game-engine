package com.burtsnyder.boxrift.libgdxcore.block;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;

public final class LibGDXBoxriftlePrimitiveRenderer {

    private LibGDXBoxriftlePrimitiveRenderer() {}

    public static void render(
            ShapeRenderer renderer,
            Boxriftle piece,
            int blockSize
    ) {
        for (Block block : piece.getBlocks()) {
            LibGDXBlockRenderer.render(renderer, block, blockSize);
        }
    }
}
