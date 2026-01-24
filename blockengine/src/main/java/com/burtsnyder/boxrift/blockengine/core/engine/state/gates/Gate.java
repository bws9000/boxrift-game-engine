package com.burtsnyder.boxrift.blockengine.core.engine.state.gates;

/**
 * controls whether an irreversible state transition
 * is permitted at a given moment in time
 */
public interface Gate {

    //returns true if the gated action is currently allowed
    //boolean isOpen();
    boolean tryOpen();

     //called when a blocking condition first occurs
    void arm();


    void reset();
}

