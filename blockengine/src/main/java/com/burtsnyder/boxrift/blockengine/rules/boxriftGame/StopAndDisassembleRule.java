package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.rules.transitions.LockKey;

public class StopAndDisassembleRule extends BaseRule {

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.RESOLUTION;
    }

    public StopAndDisassembleRule(GameState state) {
        super(state);
    }

    @Override
    public int priority() {
        return 60; // after movement/rotation/softdrop,  and before spawn
    }



    @Override
    public void apply(GameState state, RuleContext ctx) {
        var piece = state.getActivePiece();
        if (piece == null) return;

        var key = LockKey.from(piece);

        if (!state.lockGate().consumeIfReady(key)) {
            return; // lock delay not finished
        }

        state.lockActivePieceAndDisassemble();
    }



}




