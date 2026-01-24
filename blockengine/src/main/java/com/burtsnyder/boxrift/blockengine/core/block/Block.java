package com.burtsnyder.boxrift.blockengine.core.block;

import com.burtsnyder.boxrift.blockengine.util.Coord;

public record Block(
        Coord position,
        BlockSetType type,
        BlockSetColor color,
        BlockMetadata metadata
){

    public Block(Coord position, BlockSetType type) {
        this(position, type, BlockStyle.getColorForType(type), new BlockMetadata());
    }

    public Block(Coord position, BlockSetType type, BlockSetColor color) {
        this(position, type, color, new BlockMetadata());
    }

    public BlockMetadata getMetadata() {
        return metadata;
    }

    public Block withMetadata(BlockMetadata metadata) {
        return new Block(position, type, color, metadata);
    }

}
