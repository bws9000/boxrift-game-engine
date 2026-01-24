package com.burtsnyder.boxrift.libgdxcore.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.renderer.GridRenderCell;
import com.burtsnyder.boxrift.blockengine.core.renderer.GridRenderLogic;

public class LibGDXGridRenderer {

    private static ShapeRenderer shapeRenderer;
    private static Grid grid;
    private static int blockSize;

    private LibGDXGridRenderer() {}

    public static void init(Grid grid, int blockSize) {
        LibGDXGridRenderer.grid = grid;
        LibGDXGridRenderer.blockSize = blockSize;
        shapeRenderer = new ShapeRenderer();
    }

    public static void render() {
        if (shapeRenderer == null || grid == null) return;

        int rows = grid.getHeight();

        //filled blocks
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (GridRenderCell cell : GridRenderLogic.generate(grid, blockSize)) {
            int x = cell.coord().x();
            int y = cell.coord().y();

            /*Block block = grid.peek(x, y);
            if (block == null) continue;
            shapeRenderer.setColor(resolveColor(block));
            float px = x * blockSize;
            float py = (rows - 1 - y) * blockSize;
            shapeRenderer.rect(px, py, blockSize, blockSize);*/
            float py = (rows - 1 - y) * blockSize;
            float px = x * blockSize;
            Block block = grid.peek(x, y);
            if (block != null) {
                if (block.getMetadata().blinking()) {
                    shapeRenderer.setColor(Color.WHITE); //flash
                } else {
                    shapeRenderer.setColor(resolveColor(block));
                }
                shapeRenderer.rect(px, py, blockSize, blockSize);
            }

        }

        shapeRenderer.end();

        //gridlines
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);

        for (GridRenderCell cell : GridRenderLogic.generate(grid, blockSize)) {
            shapeRenderer.rect(
                    cell.pixelX(),
                    cell.pixelY(),
                    cell.width(),
                    cell.height()
            );
        }

        shapeRenderer.end();
    }

    private static Color resolveColor(Block block) {
        return switch (block.color()) {
            case CYAN   -> Color.CYAN;
            case YELLOW -> Color.YELLOW;
            case PURPLE -> Color.PURPLE;
            case GREEN  -> Color.GREEN;
            case ORANGE -> Color.ORANGE;
            case BLUE   -> Color.BLUE;
            case RED    -> Color.RED;
        };
    }

    public static void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
            shapeRenderer = null;
        }
    }
}




