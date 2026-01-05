package com.burtsnyder.boxrift.blockengine.util;

/**
 * @param x column
 * @param y row
 */
public record Coord(int x, int y) {

    public int col() {
        return x;
    }

    public int row() {
        return y;
    }

    public Coord add(int dx, int dy) {
        return new Coord(x + dx, y + dy);
    }
}


