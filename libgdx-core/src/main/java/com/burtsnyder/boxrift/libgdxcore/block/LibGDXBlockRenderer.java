package com.burtsnyder.boxrift.libgdxcore.block;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.block.BlockSetColor;

public final class LibGDXBlockRenderer {

    private LibGDXBlockRenderer() {}

    public static void render(
            ShapeRenderer renderer,
            Block block,
            int blockSize
    ) {
        renderer.setColor(mapColor(block));
        renderer.rect(
                block.getPosition().x() * blockSize,
                block.getPosition().y() * blockSize,
                blockSize,
                blockSize
        );
    }


    public static void renderAt(
            ShapeRenderer renderer,
            Block block,
            int blockSize,
            int originX,
            int originY
    ) {
        int gx = originX + block.getPosition().x();
        int gy = originY + block.getPosition().y();

        renderer.setColor(mapColor(block));
        renderer.rect(
                gx * blockSize,
                gy * blockSize,
                blockSize,
                blockSize
        );
    }


    private static Color mapColor(Block block) {
        return switch (block.getColor()) {
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

