package com.burtsnyder.boxrift.libgdxcore.block;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameRenderer;
import com.burtsnyder.boxrift.libgdxcore.view.LibGDXGridRenderer;

public class LibGDXBoxriftleRenderer implements GameRenderer {

    private final int blockSize;
    private final ShapeRenderer shapeRenderer;

    public LibGDXBoxriftleRenderer(int blockSize) {
        this.blockSize = blockSize;
        this.shapeRenderer = new ShapeRenderer();
    }

    /*@Override
    public void render(GameState state) {
        var piece = state.getActivePiece();
        if (piece == null) return;

        var origin = piece.getOrigin();

        shapeRenderer.setProjectionMatrix(LibGDXGridRenderer.projection());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Block block : piece.getBlocks()) {
            shapeRenderer.setColor(resolveColor(block));

            int worldX = origin.x() + block.getPosition().x();
            int worldY = origin.y() + block.getPosition().y();

            float x = worldX * blockSize;
            float y = worldY * blockSize;

            shapeRenderer.rect(x, y, blockSize, blockSize);
        }

        shapeRenderer.end();
    }*/
    @Override
    public void render(GameState state) {
        var piece = state.getActivePiece();
        if (piece == null) return;

        int rows = state.getGrid().getHeight();
        var origin = piece.getOrigin();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Block block : piece.getBlocks()) {
            shapeRenderer.setColor(resolveColor(block));

            int worldX = origin.x() + block.getPosition().x();
            int worldY = origin.y() + block.getPosition().y();

            float x = worldX * blockSize;
            float y = (rows - 1 - worldY) * blockSize;

            shapeRenderer.rect(x, y, blockSize, blockSize);
        }

        shapeRenderer.end();
    }





    private Color resolveColor(Block block) {
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

    public void dispose() {
        shapeRenderer.dispose();
    }
}

