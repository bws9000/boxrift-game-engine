package com.burtsnyder.boxrift.blockengine.core.engine.state.mutations;

import com.burtsnyder.boxrift.blockengine.core.engine.state.gates.Gate;

public final class GatedMutation {

    private final Gate gate;
    private final SourceAction action;

    private boolean armed = false;
    private boolean finished = false;

    // prevent same tick resolution
    private boolean armedThisTick = false;

    public GatedMutation(Gate gate, SourceAction action) {
        this.gate = gate;
        this.action = action;
    }

    public void tick() {
        if (finished) return;
/*        System.out.println("[GATED] tick armed=" + armed +
                " armedThisTick=" + armedThisTick +
                " gateOpen=" + gate.isOpen());*/

        // once
        if (!armed) {
/*            System.out.println("[GATED] ARM");*/
            gate.arm();
            action.onArm();
            armed = true;
            armedThisTick = true;
            return;
        }

        // guarantee at least one full tick alive
        if (armedThisTick) {
/*            System.out.println("[GATED] FIRST TICK");*/
            armedThisTick = false;
            action.onTick();
            return;
        }

        if (!gate.tryOpen()) {
/*            System.out.println("[GATED] BLINK TICK");*/
            action.onTick();
            return;
        }


/*        System.out.println("[GATED] RELEASE");*/

        action.onRelease();
        action.reset();
        gate.reset();
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }
}

