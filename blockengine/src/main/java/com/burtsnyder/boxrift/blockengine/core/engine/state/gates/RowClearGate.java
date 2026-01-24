package com.burtsnyder.boxrift.blockengine.core.engine.state.gates;

import com.burtsnyder.boxrift.blockengine.core.engine.EngineRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * time-based gate used to delay row clearing.
 * while the gate is closed rows are marked as pending
 * - blink mutations are allowed to run
 * once the delay expires the row clear rule is allowed to execute
 *
 */


public final class RowClearGate implements Gate {

    private static final Logger log =
            LoggerFactory.getLogger(RowClearGate.class);

    private final long delayMillis;
    private long armedAt = -1;
    private boolean consumed = false;

    public RowClearGate(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    /** returns true exactly once when delay expires */
    public boolean tryOpen() {
        if (armedAt < 0 || consumed) return false;

        if (System.currentTimeMillis() - armedAt >= delayMillis) {
            consumed = true;
            log.debug("RowClearGate CONSUMED reason=delayExpired");
            return true;
        }

        return false;
    }

/*    @Override
    public boolean isOpen() {
        return false;
    }*/

    public void arm() {
        if (armedAt < 0) {
            armedAt = System.currentTimeMillis();
            consumed = false;
        }
    }

    public void reset() {
        armedAt = -1;
        consumed = false;
        log.debug("RowClearGate RESET");
    }
}


