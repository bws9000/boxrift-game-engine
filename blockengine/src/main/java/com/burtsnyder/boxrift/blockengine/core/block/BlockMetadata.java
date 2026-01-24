package com.burtsnyder.boxrift.blockengine.core.block;

public record BlockMetadata(
        long pieceId,
        long groupId,
        boolean locked,
        boolean justSpawned,
        boolean pendingClear,
        boolean blinking
) {
    public static final long NO_PIECE = 0;
    public static final long NO_GROUP = 0;

    public BlockMetadata() {
        this(
                NO_PIECE,
                NO_GROUP,
                false,
                true,
                false,
                false
        );
    }

    public BlockMetadata markPendingClear() {
        return new BlockMetadata(
                pieceId, groupId, locked, justSpawned, true, blinking
        );
    }

    public BlockMetadata clearPending() {
        return new BlockMetadata(
                pieceId, groupId, locked, justSpawned, false, blinking
        );
    }

    public BlockMetadata withBlink(boolean blinking) {
        return new BlockMetadata(
                pieceId,
                groupId,
                locked,
                justSpawned,
                pendingClear,
                blinking
        );
    }

    public boolean isPendingClear() {
        return pendingClear;
    }

    public boolean isBlink() {
        return blinking;
    }
}
