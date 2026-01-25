package com.burtsnyder.boxrift.blockengine.core.board;

import com.burtsnyder.boxrift.blockengine.core.block.Block;

public class Cell {
    private Block block;

    public Cell() {
        this.block = null;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

}
