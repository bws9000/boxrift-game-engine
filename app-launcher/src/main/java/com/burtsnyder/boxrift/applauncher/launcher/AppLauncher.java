package com.burtsnyder.boxrift.applauncher.launcher;

import com.burtsnyder.boxrift.blockengine.platform.interfaces.GameLauncher;
import com.burtsnyder.boxrift.javafx.JavaFXLauncher;
import com.burtsnyder.boxrift.libgdx.LibGDXLauncher;

public class AppLauncher {
    public static void main(String[] args) {

        String platform = System.getProperty("platform", "javaFX");

        GameLauncher launcher = switch (platform) {
            case "libGDX" -> new LibGDXLauncher();
            default -> new JavaFXLauncher();
        };
        launcher.launch();
    }
}


