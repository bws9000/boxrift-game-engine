package com.burtsnyder.boxrift.blockengine.core.block;

public class BlockStyle {
    public static BlockSetColor getColorForType(BlockSetType type) {
        return switch (type) {
            case I -> BlockSetColor.CYAN;
            case O -> BlockSetColor.YELLOW;
            case T -> BlockSetColor.PURPLE;
            case L -> BlockSetColor.GREEN;
            case J -> BlockSetColor.ORANGE;
            case S -> BlockSetColor.RED;
            case Z -> BlockSetColor.BLUE;
        };
    }
}
