package com.burtsnyder.boxrift.blockengine.core.board.util;

import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.block.BlockMetadata;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import java.util.Map;
import java.util.Optional;

public class GridAccess {

    public static Optional<Block> getBlockAt(Grid grid, Map<Long, Block> registry, int x, int y) {
        Long blockId = grid.getBlockIdAt(x, y);
        if (blockId == null) return Optional.empty();
        return Optional.ofNullable(registry.get(blockId));
    }

    public static Optional<BlockMetadata> getMetadataAt(Grid grid, Map<Long, Block> registry, int x, int y) {
        return getBlockAt(grid, registry, x, y).map(Block::getMetadata);
    }
}
