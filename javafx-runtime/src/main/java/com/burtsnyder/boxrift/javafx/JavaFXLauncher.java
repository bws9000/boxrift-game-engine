package com.burtsnyder.boxrift.javafx;

import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameLauncher;
import com.burtsnyder.boxrift.blockengine.config.BlockConfig;
import com.burtsnyder.boxrift.javafx.view.JavaFXGameLoop;

public class JavaFXLauncher implements GameLauncher {

    @Override
    public void launch() {
        new JavaFXGameLoop(
                BlockConfig.BLOCK_SIZE,
                BlockConfig.GRID_COLUMNS,
                BlockConfig.GRID_ROWS,
                BlockConfig.GAME_NAME
        ).launchJavaFX();
    }
}
