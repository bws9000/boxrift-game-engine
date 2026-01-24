package com.burtsnyder.boxrift.libgdxcore.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.burtsnyder.boxrift.blockengine.config.BlockConfig;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.engine.EngineRunner;
import com.burtsnyder.boxrift.blockengine.core.input.InputBus;
import com.burtsnyder.boxrift.blockengine.rules.boxriftGame.*;
import com.burtsnyder.boxrift.libgdxcore.input.LibGDXKeyboardAdapter;

public class LibGDXGameLoop extends ApplicationAdapter {

    private final int blockSize;
    private final int col;
    private final int row;
    private final InputBus inputBus;
    private EngineRunner engine;
    private LibGDXBoxriftleRenderer pieceRenderer;

    public LibGDXGameLoop(
            int blockSize,
            int col,
            int row,
            InputBus inputBus,
            Grid grid
    ) {
        this.blockSize = blockSize;
        this.col = col;
        this.row = row;
        this.inputBus = inputBus;
    }

    @Override
    public void create() {
/*        ShapeRenderer debug = new ShapeRenderer();*/
        pieceRenderer = new LibGDXBoxriftleRenderer(blockSize);

        engine = new EngineRunner(
                blockSize,
                col,
                row,
                inputBus,
                pieceRenderer
        );



        var manager = engine.getManager();
        var scheduler = manager.getScheduler();

        // SENSE
        scheduler.addRule(new GroundSenseRule(manager.getState()));

        // INTENT
        scheduler.addRule(new RotationRule(manager.getState()));
        scheduler.addRule(new LateralMoveRule(manager.getState()));
        scheduler.addRule(new SoftDropRule(manager.getState()));

        // SIMULATION
        scheduler.addRule(new GravityRule(manager.getState(), BlockConfig.SCALE.gravityCellsPerSecond));

        //MUTATION
        scheduler.addRule(new RowClearBlinkingMutationRule(manager.getState()));

        // RESOLUTION
        scheduler.addRule(new StopAndDisassembleRule(manager.getState()));
        scheduler.addRule(new SpawnRule(manager.getState()));
        scheduler.addRule(new CollapseRule(manager.getState()));
        scheduler.addRule(new RowClearRule(manager.getState()));



        LibGDXGridRenderer.init(
                manager.getState().getGrid(),
                blockSize
        );

        LibGDXKeyboardAdapter.attach(inputBus);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.step(System.nanoTime());
        LibGDXGridRenderer.render();
    }

    @Override
    public void dispose() {
        pieceRenderer.dispose();
        LibGDXGridRenderer.dispose();
    }
}


