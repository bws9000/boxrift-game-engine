package com.burtsnyder.boxrift.javafx.view;

import com.burtsnyder.boxrift.blockengine.core.engine.GameLoop;
import com.burtsnyder.boxrift.blockengine.core.engine.GameManager;
import com.burtsnyder.boxrift.blockengine.core.input.InputBus;
import com.burtsnyder.boxrift.blockengine.core.input.MinimalInputBus;
import com.burtsnyder.boxrift.blockengine.core.input.keyboard.KeyboardInputSystem;
import com.burtsnyder.boxrift.blockengine.rules.GravityRule;
import com.burtsnyder.boxrift.blockengine.rules.LateralMoveRule;
import com.burtsnyder.boxrift.blockengine.rules.SoftDropRule;
import com.burtsnyder.boxrift.blockengine.rules.SpawnRule;
import com.burtsnyder.boxrift.javafx.JavaFXBoxriftleRenderer;
import com.burtsnyder.boxrift.javafx.JavaFXGridRenderer;
import com.burtsnyder.boxrift.javafx.input.JavaFXKeyboardAdapter;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXGameLoop extends GameLoop {
    private final InputBus inputBus;
    private final KeyboardInputSystem keyboard;
    private static int staticBlockSize;
    private static int col;
    private static int row;
    private static String gameName;


    public KeyboardInputSystem getKeyboard() {
        return keyboard;
    }

    public JavaFXGameLoop(int blockSize, int col, int row, String gameName) {
        this(blockSize, col, row, gameName, new MinimalInputBus());
    }
    public JavaFXGameLoop(int blockSize, int col, int row, String gameName, InputBus inputBus) {
        super(blockSize, col, row, inputBus);
        this.inputBus = inputBus;
        this.keyboard = new KeyboardInputSystem(inputBus, 170, 40);
        JavaFXGameLoop.staticBlockSize = blockSize;
        JavaFXGameLoop.col = col;
        JavaFXGameLoop.row = row;
        JavaFXGameLoop.gameName = gameName;
    }

    public void launchJavaFX() {
        Application.launch(JavaFXApp.class);
    }

    public Group getPieceLayer() {  return pieceLayer; }
    private int getBoxSize() { return staticBlockSize; }

    public static class JavaFXApp extends Application {
        public static Stage primaryStageRef;

        @Override
        public void start(Stage primaryStage) {
            primaryStageRef = primaryStage;
            JavaFXGameLoop loop = new JavaFXGameLoop(staticBlockSize, col, row,gameName);
            loop.initUI(primaryStage);
            GameManager manager = loop.getManager();

            // Spawn (-100), Lateral (0), Gravity (50)
            manager.addRule(new SpawnRule(manager.getState()));
            manager.addRule(new LateralMoveRule(manager.getState()));
            manager.addRule(new SoftDropRule(manager.getState()));    //  25
            manager.addRule(new GravityRule(manager.getState())); // defaults to 1000ms interval

            loop.setRenderer(new JavaFXBoxriftleRenderer(loop.getPieceLayer(), loop.getBoxSize()));
            loop.start();
        }


    }

    private Group pieceLayer;

    private void initUI(Stage stage) {
        Group root = new Group();
        Group gridLayer = new Group();
        pieceLayer = new Group();

        root.getChildren().addAll(gridLayer, pieceLayer);

        JavaFXGridRenderer.render(manager.getState().getGrid(), gridLayer, staticBlockSize);

        Scene scene = new Scene(
                root,
                col * staticBlockSize,
                row * staticBlockSize
        );

        stage.setScene(scene);
        stage.setTitle(gameName);
        JavaFXKeyboardAdapter.attachDefault(scene, stage, inputBus);
        scene.getRoot().requestFocus();
        stage.show();
    }

    @Override
    public void start() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                var actions = keyboard.update(now); // DAS ARR --> actions for THIS
                manager.enqueueActions(actions); // feed to rules through context
                manager.tick(); // run rules spawn --> lateral --> gravity - to drop or not to drop...
                updateView(); // render me
            }
        }.start();
    }


}

