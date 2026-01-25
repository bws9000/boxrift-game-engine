
package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.block.BlockSetType;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import com.burtsnyder.boxrift.blockengine.core.rules.interfaces.PlayerIntentMovementRule;

public class RotationRule extends BaseRule implements PlayerIntentMovementRule {

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.INTENT;
    }

    public RotationRule(GameState state) {
        super(state);
    }

    @Override
    public int priority() {
        return 20; // before gravity
    }

    @Override
    public void apply(GameState state, RuleContext ctx) {


        boolean rotate =
                ctx.input().consumeIf(a -> a == InputAction.MOVE_UP);

        if (!rotate) return;

        var active = state.getActivePiece();
        if (active == null) return;

        if(active.getType().equals(BlockSetType.O)) return;

        ctx.inhibit(RuleContext.Inhibition.GRAVITY);

        // base  rotation
        var rotated = active.rotateClockwise();

        if (state.canSpawn(rotated)) {
            state.setActivePiece(rotated);
            return;
        }

        // kick tests
        var kicks = new int[][] {
                { 1, 0 },
                { -1, 0 },
                { 0, -1 }
        };

        for (var k : kicks) {
            var kicked = rotated.move(k[0], k[1]);
            if (state.canSpawn(kicked)) {
                state.setActivePiece(kicked);
                return;
            }
        }

        // rotation failed
    }

    @Override
    public long lastIntentTick() {
        return 0;
    }

    @Override
    public void onPlayerIntent(GameState state) {

    }
}

