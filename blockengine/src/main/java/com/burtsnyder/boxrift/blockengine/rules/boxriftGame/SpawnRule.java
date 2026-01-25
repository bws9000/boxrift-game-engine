package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.actor.Boxriftle;
import com.burtsnyder.boxrift.blockengine.core.actor.BoxriftleFactory;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.block.Coord;
import com.burtsnyder.boxrift.blockengine.core.block.BlockSetType;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.VoidKey;

import java.util.Random;

public class SpawnRule extends BaseRule {

    private static final VoidKey KEY = VoidKey.INSTANCE;
    private final Random random = new Random();

    public SpawnRule(GameState state) {
        super(state);
    }

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.RESOLUTION;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {

        if (!state.spawnGate().consumeIfReady(KEY)) {
            return;
        }

        Coord spawnOrigin = state.getDefaultSpawnOrigin();

        BlockSetType[] types = BlockSetType.values();
        BlockSetType type = types[random.nextInt(types.length)];

        Boxriftle candidate =
                new BoxriftleFactory(type).createAt(spawnOrigin);

        candidate.setId(state.generateNextPieceId());
        candidate.setGroupId(state.generateNextGroupId());

        if (!state.canSpawn(candidate)) {
            state.setGameOver();
            return;
        }

        state.setActivePiece(candidate);

    }
}



