package com.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.rules.base.BaseRule;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleDomainEnum;
import java.util.function.LongSupplier;
import static com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext.Inhibition.GRAVITY;

public class GravityRule extends BaseRule {
    private final LongSupplier clockNanos;
    private final long intervalNanos;
    private long lastDropAt = 0;

    @Override
    public RuleDomainEnum domain() {
        return RuleDomainEnum.SIMULATION;
    }

    public GravityRule(GameState state) {
        this(state, System::nanoTime, 1000);
        //System.out.println("Gravity Rule Initialized");
    }
    public GravityRule(GameState state, long gravityMs) {
        this(state, System::nanoTime, gravityMs);
    }


    public GravityRule(GameState state, LongSupplier clockNanos, long gravityMs) {
        super(state);
        this.clockNanos= clockNanos;
        this.intervalNanos = gravityMs * 1_000_000L;
    }

    @Override
    public int priority() {
        return 50;
    }


    @Override
    public void apply(GameState state, RuleContext ctx) {
        var piece = state.getActivePiece();
        if (piece == null) return;

        // observe grounded state
        if (!state.canMoveActive(0, 1)) {
            ctx.currentFrame().setGravityBlocked(true);
        }

        //may still be inhibited
        if (ctx.isInhibited(GRAVITY)) return;

        long now = clockNanos.getAsLong();
        if (now - lastDropAt < intervalNanos) return;

        if (state.canMoveActive(0, 1)) {
            state.setActivePiece(piece.move(0, 1));
            lastDropAt = now;
        }
    }


    /*@Override
    public void apply(GameState state, RuleContext ctx) {
        if (ctx.isInhibited(GRAVITY)) return;

        long now = clockNanos.getAsLong();
        if (now - lastDropAt < intervalNanos) return;

        var piece = state.getActivePiece();
        if (piece == null) return;

*//*        if (state.canMoveActive(0, 1)) {
            state.setActivePiece(piece.move(0, 1));
            lastDropAt = now;
            return;
        }*//*
        if (!state.canMoveActive(0, 1)) {
            ctx.currentFrame().setGravityBlocked(true);
        } else {
            state.setActivePiece(state.getActivePiece().move(0, 1));
            lastDropAt = now;
            return;
        }


        lastDropAt = now;
    }*/


    /*@Override
    public void apply(GameState state, RuleContext ctx) {

        if (ctx.isInhibited(GRAVITY)) return;

        long now = clockNanos.getAsLong();
        if (now - lastDropAt < intervalNanos) return;

        var piece = state.getActivePiece();
        if (piece == null) return;

*//*        var down = piece.move(0, 1);*//*

*//*        if (state.isValidPosition(down)) {
            state.setActivePiece(down);
            lastDropAt = now;
            return;
        }*//*
        if (state.canMoveActive(0, 1)) {
*//*            state.setActivePiece(piece.move(0, 1));
            lastDropAt = now;*//*
            state.notifyDownwardBlocked();
            return;
        }
        state.setActivePiece(piece.move(0, 1));

        if (!state.isDownwardBlockedThisTick()) return;
        if (state.didPieceMove()) return;
        state.lockActivePieceAndDisassemble();
        state.clearActivePiece();

        state.notifyDownwardBlocked();
        //state.lockActivePieceAndDisassemble();
        lastDropAt = now;
    }*/
}
