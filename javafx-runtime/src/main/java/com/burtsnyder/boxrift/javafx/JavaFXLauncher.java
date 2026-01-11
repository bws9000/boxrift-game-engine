package com.burtsnyder.boxrift.javafx;

import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameLauncher;

public class JavaFXLauncher implements GameLauncher {

    @Override
    public void launch() {
        JavaFXMain.main(new String[0]);
    }
}

