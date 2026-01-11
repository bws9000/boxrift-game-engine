package com.burtsnyder.boxrift.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class LibGDXGame extends ApplicationAdapter {
    private final int blockSize;
    private final int columns;
    private final int rows;
    private final String gameName;

    public LibGDXGame(int blockSize, int columns, int rows, String gameName) {
        this.blockSize = blockSize;
        this.columns = columns;
        this.rows = rows;
        this.gameName = gameName;
    }

    @Override
    public void create() {
        System.out.println("Launching " + gameName);
        System.out.println("Grid: " + columns + "x" + rows + ", block=" + blockSize);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
