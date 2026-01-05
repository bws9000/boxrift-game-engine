package com.burtsnyder.boxrift.blockengine.core.board;

import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.block.BlockMetadata;

public class Cell {
    private Block block;

    public Cell() {
        this.block = null;
        BlockMetadata metadata = null;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Long getBlockId() {
        return block != null ? block.getMetadata().pieceId() : null;
    }
}
