package com.burtsnyder.boxrift.blockengine.config;


public final class BlockConfig {

    // fixed board size
    public static final int BOARD_WIDTH_PX  = 380;
    public static final int BOARD_HEIGHT_PX = 760;

    // choose ONE scale
    public static final BlockScale SCALE = BlockScale.SMALL;

    // derived values
    public static final int BLOCK_SIZE = SCALE.blockSize;
    public static final int GRID_COLUMNS = BOARD_WIDTH_PX  / BLOCK_SIZE;
    public static final int GRID_ROWS    = BOARD_HEIGHT_PX / BLOCK_SIZE;

    static {
        if (BOARD_WIDTH_PX % BLOCK_SIZE != 0 ||
                BOARD_HEIGHT_PX % BLOCK_SIZE != 0) {
            throw new IllegalStateException(
                    "block size does not evenly divide board size"
            );
        }
    }

    public static final String GAME_NAME = "Boxrift";

    private BlockConfig() {}
}



/*public final class BlockConfig {
    public static final int BLOCK_SIZE = 38;
    public static final int GRID_COLUMNS = 10;
    public static final int GRID_ROWS = 20;
    public static final String GAME_NAME = "Boxrift";

    private BlockConfig() {}
}*/

