package com.burtsnyder.boxrift.libgdxcore.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameRenderer;
import com.burtsnyder.boxrift.blockengine.core.block.Block;

import static com.burtsnyder.boxrift.libgdxcore.view.LibGDXGridRenderer.getColor;

public class LibGDXBoxriftleRenderer implements GameRenderer {

    private final int blockSize;
    private final ShapeRenderer shapeRenderer;

    public LibGDXBoxriftleRenderer(int blockSize) {
        this.blockSize = blockSize;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(GameState state) {
        var piece = state.getActivePiece();
        if (piece == null) return;

        int rows = state.getGrid().getHeight();
        var origin = piece.getOrigin();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Block block : piece.getBlocks()) {
            shapeRenderer.setColor(resolveColor(block));

            int worldX = origin.x() + block.position().x();
            int worldY = origin.y() + block.position().y();

            float x = worldX * blockSize;
            float y = (rows - 1 - worldY) * blockSize;

            shapeRenderer.rect(x, y, blockSize, blockSize);
        }

        shapeRenderer.end();
    }



    private Color resolveColor(Block block) {
        return getColor(block);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}

