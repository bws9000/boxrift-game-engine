package com.burtsnyder.boxrift.blockengine.core.block;

public record BlockMetadata(long pieceId, long groupId, boolean locked, boolean justSpawned) {
    public static final long NO_PIECE = 0;
    public static final long NO_GROUP = 0;

    public BlockMetadata() {
        this(NO_PIECE, NO_GROUP, false, true);
    }

    public BlockMetadata copyWith(
            Long pieceId,
            Long groupId,
            Boolean locked,
            Boolean justSpawned
    ) {
        return new BlockMetadata(
                pieceId != null ? pieceId : this.pieceId,
                groupId != null ? groupId : this.groupId,
                locked != null ? locked : this.locked,
                justSpawned != null ? justSpawned : this.justSpawned
        );
    }
}
