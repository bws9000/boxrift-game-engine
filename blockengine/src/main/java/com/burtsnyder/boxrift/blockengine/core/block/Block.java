package com.burtsnyder.boxrift.blockengine.core.block;

import com.burtsnyder.boxrift.blockengine.util.Coord;

public class Block {
    private final Coord position;
    private final BlockSetType type;
    private final BlockSetColor color;
    private final BlockMetadata blockMetadata;

    public Block(Coord position, BlockSetType type) {
        this(position, type, BlockStyle.getColorForType(type), new BlockMetadata());
    }

    public Block(Coord position, BlockSetType type, BlockSetColor color) {
        this(position, type, color, new BlockMetadata());
    }

    public Block(Coord position, BlockSetType type, BlockSetColor color, BlockMetadata blockMetadata) {
        this.position = position;
        this.type = type;
        this.color = color;
        this.blockMetadata = blockMetadata;
    }

    public Coord getPosition() {
        return position;
    }

    public BlockSetType getType() {
        return type;
    }

    public BlockSetColor getColor() {
        return color;
    }

    public BlockMetadata getMetadata() {
        return blockMetadata;
    }


}
