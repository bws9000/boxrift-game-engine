package com.burtsnyder.boxrift.javafx.view;

import com.burtsnyder.boxrift.blockengine.core.engine.GameLoop;
import com.burtsnyder.boxrift.blockengine.core.input.InputBus;
import com.burtsnyder.boxrift.blockengine.core.input.MinimalInputBus;
import com.burtsnyder.boxrift.blockengine.core.input.keyboard.KeyboardInputSystem;
import com.burtsnyder.boxrift.javafx.JavaFXGridRenderer;
import com.burtsnyder.boxrift.javafx.input.JavaFXKeyboardAdapter;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXGameLoop extends GameLoop {

    private final InputBus inputBus;
    private final KeyboardInputSystem keyboard;

    private final int blockSize;
    private final int col;
    private final int row;
    private final String gameName;

    private Group pieceLayer;
    private AnimationTimer timer;

    public JavaFXGameLoop(int blockSize, int col, int row, String gameName) {
        this(blockSize, col, row, gameName, new MinimalInputBus());
    }

    public JavaFXGameLoop(
            int blockSize,
            int col,
            int row,
            String gameName,
            InputBus inputBus
    ) {
        super(blockSize, col, row, inputBus);
        this.blockSize = blockSize;
        this.col = col;
        this.row = row;
        this.gameName = gameName;
        this.inputBus = inputBus;
        this.keyboard = new KeyboardInputSystem(inputBus, 170, 40);
    }

    public void initialize(Stage stage) {
        Group root = new Group();
        Group gridLayer = new Group();
        pieceLayer = new Group();

        root.getChildren().addAll(gridLayer, pieceLayer);

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

        stage.setTitle(gameName);
        stage.setScene(scene);
        stage.show();

        scene.getRoot().requestFocus();
    }


    @Override
    public void start() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                var actions = keyboard.update(now);
                manager.enqueueActions(actions);
                manager.tick();
                updateView();
            }
        };
        timer.start();
    }


    public Group getPieceLayer() {
        return pieceLayer;
    }
}


