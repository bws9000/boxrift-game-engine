package com.burtsnyder.boxrift.libgdxcore.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.renderer.GridRenderCell;
import com.burtsnyder.boxrift.blockengine.core.renderer.GridRenderLogic;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy.BlinkPolicy;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.policy.ToggleBlinkPolicy;

public class LibGDXGridRenderer {
    private static BlinkPolicy blinkPolicy = new ToggleBlinkPolicy(6);
    private static ShapeRenderer shapeRenderer;
    private static Grid grid;
    private static int blockSize;

    private LibGDXGridRenderer() {}

    public static void init(Grid grid, int blockSize) {
        LibGDXGridRenderer.grid = grid;
        LibGDXGridRenderer.blockSize = blockSize;
        shapeRenderer = new ShapeRenderer();
    }

    public static void render(GameState state) {
        if (shapeRenderer == null || grid == null) return;

        int rows = grid.getHeight();

        // filled blocks
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (GridRenderCell cell : GridRenderLogic.generate(grid, blockSize)) {
            int x = cell.coord().x();
            int y = cell.coord().y();
            float py = (rows - 1 - y) * blockSize;
            float px = x * blockSize;
            Block block = grid.peek(x, y);
            if (block != null) {

                if (block.getMetadata().pendingClear()) {
                    long start = state.getBlinkStartFrame(y).orElse(state.tick);
                    if (!blinkPolicy.isVisible(state.tick, start)) {
                        continue;
                    }
                }
                shapeRenderer.setColor(resolveColor(block));
                shapeRenderer.rect(px, py, blockSize, blockSize);
            }
        }

        shapeRenderer.end();

        // gridlines
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
        return getColor(block);
    }

    static Color getColor(Block block) {
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




