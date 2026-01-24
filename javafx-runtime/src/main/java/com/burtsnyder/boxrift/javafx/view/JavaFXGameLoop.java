package com.burtsnyder.boxrift.javafx.view;

import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.engine.GameLoop;
import com.burtsnyder.boxrift.blockengine.core.input.InputBus;
import com.burtsnyder.boxrift.blockengine.core.input.MinimalInputBus;
import com.burtsnyder.boxrift.blockengine.core.input.keyboard.KeyboardInputSystem;
import com.burtsnyder.boxrift.javafx.JavaFXGridRenderer;
import com.burtsnyder.boxrift.javafx.block.JavaFXGridBlockRenderer;
import com.burtsnyder.boxrift.javafx.input.JavaFXKeyboardAdapter;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXGameLoop extends GameLoop {

    private final InputBus inputBus;
    private final KeyboardInputSystem keyboard;

    private Group lockedLayer;
    private Group pieceLayer;

    public JavaFXGameLoop(int blockSize, int col, int row, String gameName) {
        this(
                blockSize,
                col,
                row,
                gameName,
                new MinimalInputBus(),
                new Grid(col, row)
        );
    }

    public JavaFXGameLoop(
            int blockSize,
            int col,
            int row,
            String gameName,
            InputBus inputBus,
            Grid grid
    ) {
        super(blockSize, col, row, inputBus, grid);
        this.inputBus = inputBus;
        this.keyboard = new KeyboardInputSystem(inputBus, 170, 40);
    }

    @Override
    public void start() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                var actions = keyboard.update(now);
                manager.enqueueActions(actions);
                manager.tick();

                lockedLayer.getChildren().clear();

                JavaFXGridBlockRenderer.render(
                        manager.getState().getGrid(),
                        lockedLayer,
                        blockSize
                );


                updateView();
            }
        };
        timer.start();
    }

    public void attach(Stage stage, Group root) {
        Group gridLayer = new Group();
        lockedLayer = new Group();
        pieceLayer = new Group();

        root.getChildren().addAll(
                lockedLayer,
                pieceLayer,
                gridLayer
        );

        JavaFXGridRenderer.render(manager.getState().getGrid(), gridLayer, blockSize);


        JavaFXGridRenderer.render(
                manager.getState().getGrid(),
                gridLayer,
                blockSize
        );

        Scene scene = new Scene(
                root,
                col * blockSize,
                row * blockSize
        );

        JavaFXKeyboardAdapter.attachDefault(scene, stage, inputBus);
        stage.setScene(scene);
        stage.show();
        scene.getRoot().requestFocus();
    }

    public Group getPieceLayer() {
        return pieceLayer;
    }
}



