package com.burtsnyder.boxrift.libgdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameLauncher;

//DESKTOP
public class LibGDXLauncher implements GameLauncher {

    @Override
    public void launch() {
        Lwjgl3ApplicationConfiguration config =
                new Lwjgl3ApplicationConfiguration();

        config.setTitle("Boxrift");
        config.setWindowedMode(800, 600);

        new Lwjgl3Application(new LibGDXGame(), config);
    }
}

