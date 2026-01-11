package com.burtsnyder.boxrift.javafx;

import com.burtsnyder.boxrift.blockengine.config.BlockConfig;
import com.burtsnyder.boxrift.blockengine.rules.boxriftGame.*;
import com.burtsnyder.boxrift.javafx.view.JavaFXGameLoop;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXApplication extends Application {

    @Override
    public void start(Stage stage) {

        JavaFXGameLoop loop = new JavaFXGameLoop(
                BlockConfig.BLOCK_SIZE,
                BlockConfig.GRID_COLUMNS,
                BlockConfig.GRID_ROWS,
                BlockConfig.GAME_NAME
        );

        loop.initialize(stage);

        var manager = loop.getManager();
        //manager.addRule(new StopAndSpawnRule(manager.getState()));
        manager.addRule(new RotationRule(manager.getState()));
        manager.addRule(new SpawnRule(manager.getState()));
        manager.addRule(new LateralMoveRule(manager.getState()));
        manager.addRule(new SoftDropRule(manager.getState()));
        manager.addRule(new GravityRule(manager.getState()));

        loop.setRenderer(
                new JavaFXBoxriftleRenderer(
                        loop.getPieceLayer(),
                        BlockConfig.BLOCK_SIZE
                )
        );


        loop.start();
    }

}


