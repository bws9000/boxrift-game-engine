package com.burtsnyder.boxrift.javafx;

import com.burtsnyder.boxrift.blockengine.config.BlockConfig;
import com.burtsnyder.boxrift.blockengine.rules.boxriftGame.*;
import com.burtsnyder.boxrift.javafx.view.JavaFXGameLoop;
import javafx.application.Application;
import javafx.stage.Stage;
import com.burtsnyder.boxrift.blockengine.rules.boxriftGame.*;
import javafx.scene.Group;
import com.burtsnyder.boxrift.blockengine.rules.boxriftGame.*;

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
        scheduler.addRule(new GroundSenseRule(manager.getState()));
        scheduler.addRule(new RotationRule(manager.getState()));
        scheduler.addRule(new LateralMoveRule(manager.getState()));
        scheduler.addRule(new SoftDropRule(manager.getState()));
        scheduler.addRule(new GravityRule(manager.getState()));
        scheduler.addRule(new StopAndDisassembleRule(manager.getState()));
        scheduler.addRule(new SpawnRule(manager.getState()));
        scheduler.addRule(new RowClearRule(manager.getState()));
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


