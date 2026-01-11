package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.input.InputAction;
import com.burtsnyder.boxrift.blockengine.core.rules.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.RuleContext;

public class RotationRule extends BaseRule {

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
        //System.out.println("*RotationRule** MOVE_UP detected ->rotate clockwise");

        var active = state.getActivePiece();
        if (active == null) return;

/*        System.out.println("before rotate ");
        active.getBlocks().forEach(b ->
                System.out.println("  " + b.getPosition())
        );*/

        var rotated = active.rotateClockwise();

/*        System.out.println("after rotate ");
        rotated.getBlocks().forEach(b ->
                System.out.println("  " + b.getPosition())
        );*/


        ctx.inhibit(RuleContext.Inhibition.GRAVITY);

        if (state.isValidPosition(rotated)) {
            state.setActivePiece(rotated);
            return;
        }


        for (var offset : state.getRotationMoves()) {
            var rotatedRight = rotated.move(offset.x(), offset.y());
            if (state.isValidPosition(rotatedRight)) {
                state.setActivePiece(rotatedRight);
                return;
            }
        }






        /*for (var kick : state.getRotationKicks()) {
            var kicked = rotated.move(kick.x(), kick.y());
            if (state.isValidPosition(kicked)) {
                state.setActivePiece(kicked);
                return;
            }
        }*/



        // ...
    }

}
