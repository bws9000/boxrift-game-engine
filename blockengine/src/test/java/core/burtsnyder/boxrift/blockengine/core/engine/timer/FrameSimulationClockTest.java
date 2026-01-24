package core.burtsnyder.boxrift.blockengine.core.engine.timer;


import com.burtsnyder.boxrift.blockengine.core.engine.FrameState;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.engine.timer.FrameSimulationClock;
import com.burtsnyder.boxrift.blockengine.core.input.FrameInput;
import com.burtsnyder.boxrift.blockengine.core.rules.RuleScheduler;
import com.burtsnyder.boxrift.blockengine.rules.boxriftGame.FrameProbeRule;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FrameSimulationClockTest {

    @Test
    void step_swaps_frames_correctly() {

        GameState state = new GameState(10, 20);
        RuleScheduler scheduler = new RuleScheduler();
        FrameInput input = new FrameInput();

        FrameSimulationClock clock =
                new FrameSimulationClock(state, scheduler, input);

        //  bootstrap frame
        FrameState initial = clock.getCurrentFrame();
        assertNotNull(initial);

        // step
        clock.step();
        FrameState first = clock.getCurrentFrame();

        assertNotSame(initial, first,
                "first step must create a new frame");

        // step again
        clock.step();
        FrameState second = clock.getCurrentFrame();

        assertNotSame(first, second,
                "each step must create a new frame");
    }




}

