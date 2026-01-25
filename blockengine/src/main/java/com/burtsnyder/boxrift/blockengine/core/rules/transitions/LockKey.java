package com.burtsnyder.boxrift.blockengine.core.rules.transitions;

import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;
import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.TransitionKey;
import com.burtsnyder.boxrift.blockengine.core.types.Rotation;
import com.burtsnyder.boxrift.blockengine.core.block.Coord;

public record LockKey(long pieceId) implements TransitionKey {

    public static LockKey from(Boxriftle piece) {
        return new LockKey(piece.getId());
    }
}




