package com.burtsnyder.boxrift.libgdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.burtsnyder.boxrift.blockengine.config.BlockConfig;
import com.burtsnyder.boxrift.blockengine.core.input.MinimalInputBus;
import com.burtsnyder.boxrift.libgdxcore.view.LibGDXGameLoop;

public class DesktopLauncher {

    public static void main(String[] args) {
        launch();
    }

    public static void launch() {
        Lwjgl3ApplicationConfiguration config =
                new Lwjgl3ApplicationConfiguration();

        config.setTitle(BlockConfig.GAME_NAME);
        config.setWindowedMode(
                BlockConfig.GRID_COLUMNS * BlockConfig.BLOCK_SIZE,
                BlockConfig.GRID_ROWS * BlockConfig.BLOCK_SIZE
        );

        new Lwjgl3Application(
                new LibGDXGameLoop(
                        BlockConfig.BLOCK_SIZE,
                        BlockConfig.GRID_COLUMNS,
                        BlockConfig.GRID_ROWS,
                        new MinimalInputBus()
                ),
                config
        );


        /*new Lwjgl3Application(
                new LibGDXGame(
                        BlockConfig.BLOCK_SIZE,
                        BlockConfig.GRID_COLUMNS,
                        BlockConfig.GRID_ROWS,
                        BlockConfig.GAME_NAME
                ),
                config
        );*/
    }
}

