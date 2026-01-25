package com.burtsnyder.boxrift.blockengine.config;

public enum BlockScale {

    INSANE_SMALL(5,  20),
    TOTALLY_SMALL(10, 12),
    SUPER_SMALL(19,  8),
    SMALL(38,        3),
    MEDIUM(76,       2);

    public final int blockSize;
    public final int gravityCellsPerSecond;

    BlockScale(int blockSize, int gravityCellsPerSecond) {
        this.blockSize = blockSize;
        this.gravityCellsPerSecond = gravityCellsPerSecond;
    }
}


