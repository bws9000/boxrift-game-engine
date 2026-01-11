package com.burtsnyder.boxrift.blockengine.core.actor;

import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.block.BlockSetColor;
import com.burtsnyder.boxrift.blockengine.core.block.BlockSetType;
import com.burtsnyder.boxrift.blockengine.core.types.Rotation;
import com.burtsnyder.boxrift.blockengine.util.Coord;

import java.util.List;

public class Boxriftle extends Actor {
    private final BlockSetType type;
    private final Rotation rotation;

    public Boxriftle(BlockSetType type,
                     List<Block> blocks,
                     Coord origin,
                     Rotation rotation) {
        super(origin, blocks);
        this.type = type;
        this.rotation = rotation;
    }

    public BlockSetType getType() { return type; }
    public Rotation getRotation() { return rotation; }

    @Override
    public Boxriftle move(int dx, int dy) {
        Coord newOrigin = origin.add(dx, dy);
        Boxriftle moved = new Boxriftle(type, blocks, newOrigin, rotation);
        moved.setId(this.id);
        moved.setGroupId(this.groupId);
        return moved;
    }

    public Boxriftle rotateClockwise() {
        return this;
    }

    public Boxriftle rotateCounterClockwise() {
        return this;
    }

    public Boxriftle withColor(BlockSetColor color) {
        List<Block> recolored = blocks.stream()
                .map(b -> new Block(b.getPosition(), b.getType(), color))
                .toList();
        Boxriftle copy = new Boxriftle(type, recolored, origin, rotation);
        copy.setId(this.id);
        copy.setGroupId(this.groupId);
        return copy;
    }
}
