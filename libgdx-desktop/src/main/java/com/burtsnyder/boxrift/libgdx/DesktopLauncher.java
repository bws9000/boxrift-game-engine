package com.burtsnyder.boxrift.libgdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.burtsnyder.boxrift.blockengine.config.BlockConfig;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.input.MinimalInputBus;
import com.burtsnyder.boxrift.libgdxcore.view.LibGDXGameLoop;
import org.slf4j.LoggerFactory;

public class DesktopLauncher {

    public static void main(String[] args) {
        System.out.println("ILoggerFactory  = " + LoggerFactory.getILoggerFactory().getClass());
        launch();
    }

    public static void launch() {
        int gridWidth = BlockConfig.GRID_COLUMNS * BlockConfig.BLOCK_SIZE;
        int gridHeight = BlockConfig.GRID_ROWS * BlockConfig.BLOCK_SIZE;

        Lwjgl3ApplicationConfiguration config =
                new Lwjgl3ApplicationConfiguration();

        config.setTitle(BlockConfig.GAME_NAME);
        config.setWindowedMode(
                gridWidth,
                gridHeight
        );

        new Lwjgl3Application(
                new LibGDXGameLoop(
                        BlockConfig.BLOCK_SIZE,
                        BlockConfig.GRID_COLUMNS,
                        BlockConfig.GRID_ROWS,
                        new MinimalInputBus(),
                        new Grid(gridWidth, gridHeight)
                ),
                config
        );



    }
}

