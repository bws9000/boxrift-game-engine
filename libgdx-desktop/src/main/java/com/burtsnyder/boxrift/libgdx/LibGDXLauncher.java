package com.burtsnyder.boxrift.libgdx;

import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameLauncher;

public class LibGDXLauncher implements GameLauncher {

    @Override
    public void launch() {
        DesktopLauncher.launch();
    }
}

