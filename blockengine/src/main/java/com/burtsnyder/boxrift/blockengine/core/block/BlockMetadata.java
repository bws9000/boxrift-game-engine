package com.burtsnyder.boxrift.blockengine.core.block;

public record BlockMetadata(long pieceId, long groupId, boolean locked, boolean justSpawned) {
    public static final long NO_PIECE = 0;
    public static final long NO_GROUP = 0;

    public BlockMetadata() {
        this(NO_PIECE, NO_GROUP, false, true);
    }

}
