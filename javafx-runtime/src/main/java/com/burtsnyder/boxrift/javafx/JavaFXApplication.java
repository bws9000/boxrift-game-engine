package com.burtsnyder.boxrift.javafx;

import com.burtsnyder.boxrift.blockengine.config.BlockConfig;
import com.burtsnyder.boxrift.blockengine.core.rules.LockEligibilityRule;
import com.burtsnyder.boxrift.blockengine.rules.boxriftGame.*;
import com.burtsnyder.boxrift.javafx.view.JavaFXGameLoop;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;

import java.util.concurrent.locks.Lock;

public class JavaFXApplication extends Application {

    @Override
    public void start(Stage stage) {

        JavaFXGameLoop loop = new JavaFXGameLoop(
                BlockConfig.BLOCK_SIZE,
                BlockConfig.GRID_COLUMNS,
                BlockConfig.GRID_ROWS,
                BlockConfig.GAME_NAME
        );

        Group root = new Group();
        loop.attach(stage, root);

        var manager = loop.getManager();
        var scheduler = manager.getScheduler();

        // ENGINE
        scheduler.addRule(new SpawnEligibilityRule(manager.getState()));
        scheduler.addRule(new LockEligibilityRule(manager.getState()));
        scheduler.addRule(new RowClearEligibilityRule(manager.getState()));

        // SENSE
        scheduler.addRule(new GroundSenseRule(manager.getState()));

        // INTENT
        scheduler.addRule(new RotationRule(manager.getState()));
        scheduler.addRule(new LateralMoveRule(manager.getState()));
        scheduler.addRule(new SoftDropRule(manager.getState()));

        // SIMULATION
        scheduler.addRule(new GravityRule(
                manager.getState(),
                BlockConfig.SCALE.gravityCellsPerSecond
        ));

        // MUTATION
        scheduler.addRule(new RowClearMutationRule(manager.getState()));

        // RESOLUTION
        scheduler.addRule(new StopAndDisassembleRule(manager.getState()));
        scheduler.addRule(new SpawnRule(manager.getState()));
        scheduler.addRule(new CollapseRule(manager.getState()));



        loop.setRenderer(
                new JavaFXBoxriftleRenderer(
                        loop.getPieceLayer(),
                        BlockConfig.BLOCK_SIZE
                )
        );

        loop.start();
    }
}


