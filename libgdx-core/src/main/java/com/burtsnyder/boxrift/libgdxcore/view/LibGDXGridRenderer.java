package com.burtsnyder.boxrift.libgdxcore.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
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
        if (shapeRenderer == null) return;

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

    public static void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
            shapeRenderer = null;
        }
    }
}



