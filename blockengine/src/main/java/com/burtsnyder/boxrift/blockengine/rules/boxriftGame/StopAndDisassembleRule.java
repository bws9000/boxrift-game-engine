package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;

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
        if (state.getActivePiece() == null) return;

        if (state.canMoveActive(0, 1)) return;


        boolean wasBlocked = ctx.previousFrame().gravityBlocked();
        boolean isBlocked  = ctx.currentFrame().gravityBlocked();


        if (wasBlocked && isBlocked) {
            state.lockActivePieceAndDisassemble();
        }
    }


}




