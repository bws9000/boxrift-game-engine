package com.burtsnyder.boxrift.blockengine.core.actor;

import com.burtsnyder.boxrift.blockengine.util.Coord;
import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.block.BlockSetType;
import java.util.List;

public record BoxriftleFactory(BlockSetType type) {

    private List<Block> localBlocksFor(BlockSetType t) {
        switch (t) {
            case I:
                return List.of(
                        new Block(new Coord(-1, 0), t),
                        new Block(new Coord(0, 0), t),
                        new Block(new Coord(1, 0), t),
                        new Block(new Coord(2, 0), t)
                );
            case O:
                return List.of(
                        new Block(new Coord(0, 0), t),
                        new Block(new Coord(1, 0), t),
                        new Block(new Coord(0, 1), t),
                        new Block(new Coord(1, 1), t)
                );
            case T:
                return List.of(
                        new Block(new Coord(-1, 0), t),
                        new Block(new Coord(0, 0), t),
                        new Block(new Coord(1, 0), t),
                        new Block(new Coord(0, 1), t)
                );
            case J:
                return List.of(
                        new Block(new Coord(-1, 0), t),
                        new Block(new Coord(0, 0), t),
                        new Block(new Coord(1, 0), t),
                        new Block(new Coord(-1, 1), t)
                );
            case L:
                return List.of(
                        new Block(new Coord(-1, 0), t),
                        new Block(new Coord(0, 0), t),
                        new Block(new Coord(1, 0), t),
                        new Block(new Coord(1, 1), t)
                );
            case S:
                return List.of(
                        new Block(new Coord(0, 0), t),
                        new Block(new Coord(1, 0), t),
                        new Block(new Coord(-1, 1), t),
                        new Block(new Coord(0, 1), t)
                );
            case Z:
                return List.of(
                        new Block(new Coord(-1, 0), t),
                        new Block(new Coord(0, 0), t),
                        new Block(new Coord(0, 1), t),
                        new Block(new Coord(1, 1), t)
                );
            default:
                throw new IllegalArgumentException("Unhandled BlockSetType: " + t);
        }
    }

    public Boxriftle createAt(Coord origin) {
        var blocks = localBlocksFor(type);
        return new Boxriftle(type, blocks, origin, null);
    }
}

