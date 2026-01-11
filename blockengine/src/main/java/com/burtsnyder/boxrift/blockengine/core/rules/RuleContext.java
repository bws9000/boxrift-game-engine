package com.burtsnyder.boxrift.blockengine.core.rules;

import com.burtsnyder.boxrift.blockengine.core.input.FrameInput;

import java.security.Key;
import java.util.EnumSet;



public final class RuleContext {
    public enum Inhibition { GRAVITY }

    private final EnumSet<Inhibition> inhibitions = EnumSet.noneOf(Inhibition.class);
    private final FrameInput input;

    public RuleContext(FrameInput input) { this.input = input; }

    public FrameInput input() { return input; }

    public void inhibit(Inhibition i) { inhibitions.add(i); }
    public boolean isInhibited(Inhibition i) { return inhibitions.contains(i); }

    public void reset() { inhibitions.clear(); input.clear(); }

}
