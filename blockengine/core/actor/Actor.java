package com.burtsnyder.blockengine.core.actor;

import com.burtsnyder.blockengine.core.block.Block;
import com.burtsnyder.blockengine.util.Coord;
import java.util.List;

public abstract class Actor {
    protected Coord origin;
    protected List<Block> blocks;

    protected long id;
    protected Long groupId = null;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Actor(Coord origin, List<Block> blocks) {
        this.origin = origin;
        this.blocks = blocks;
    }

    public Actor() {}

    public List<Block> getBlocks() {
        return blocks;
    }

    public Coord getOrigin() {
        return origin;
    }

    public abstract Actor move(int dx, int dy);
}
