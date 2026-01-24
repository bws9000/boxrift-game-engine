package com.burtsnyder.boxrift.blockengine.core.actor;

import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.block.BlockSetType;
import com.burtsnyder.boxrift.blockengine.core.types.Rotation;
import com.burtsnyder.boxrift.blockengine.util.Coord;

import java.util.List;

public class Boxriftle extends Actor {
    private final BlockSetType type;
    private final Rotation rotation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boxriftle other)) return false;

        return origin.equals(other.origin)
                && rotation == other.rotation
                && type == other.type;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(origin, rotation, type);
    }


    public Boxriftle(BlockSetType type,
                     List<Block> blocks,
                     Coord origin,
                     Rotation rotation) {
        super(origin, blocks);
        this.type = type;
        this.rotation = rotation;
    }

    public BlockSetType getType() { return type; }
    //public Rotation getRotation() { return rotation; }

    @Override
    public Boxriftle move(int dx, int dy) {
        Coord newOrigin = origin.add(dx, dy);
        Boxriftle moved = new Boxriftle(type, blocks, newOrigin, rotation);
        moved.setId(this.id);
        moved.setGroupId(this.groupId);
        return moved;
    }

    public Boxriftle rotateClockwise() {
        List<Block> rotatedBlocks = blocks.stream()
                .map(b -> {
                    Coord p = b.position();
                    Coord rotated = new Coord(p.y(), -p.x());

                    return new Block(
                            rotated,
                            b.type(),
                            b.color(),
                            b.getMetadata()
                    );
                })
                .toList();

        Boxriftle rotatedPiece = new Boxriftle(
                type,
                rotatedBlocks,
                origin,
                rotation
        );

        rotatedPiece.setId(this.id);
        rotatedPiece.setGroupId(this.groupId);

        return rotatedPiece;
    }

}
