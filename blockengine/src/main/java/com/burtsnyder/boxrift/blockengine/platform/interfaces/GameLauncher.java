package com.burtsnyder.boxrift.blockengine.platform.interfaces;

/**
 * Platform entry adapter

 * Implementations are responsible for bootstrapping
 * a concrete runtime (JavaFX, LibGDX, Headless, etc
 *
 * This interface intentionally contains NO UI concepts
 */
public interface GameLauncher {

    /**
     * Launch the game on the target platform.
     *
     * this method must:
     * - create the platform window/context
     * - start the main game loop
     */
    void launch();
}

