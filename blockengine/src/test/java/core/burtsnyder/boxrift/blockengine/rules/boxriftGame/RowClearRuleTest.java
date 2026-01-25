package core.burtsnyder.boxrift.blockengine.rules.boxriftGame;

import com.burtsnyder.boxrift.blockengine.core.block.BlockSetType;
import com.burtsnyder.boxrift.blockengine.core.board.Grid;
import com.burtsnyder.boxrift.blockengine.core.block.Block;
import com.burtsnyder.boxrift.blockengine.core.engine.GameState;
import com.burtsnyder.boxrift.blockengine.core.input.FrameInput;
import com.burtsnyder.boxrift.blockengine.core.rules.base.RuleContext;
import com.burtsnyder.boxrift.blockengine.rules.boxriftGame.RowClearEligibilityRule;
import com.burtsnyder.boxrift.blockengine.core.block.Coord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RowClearRuleTest {

    private List<List<Block>> snapshotRows(Grid grid) {
        List<List<Block>> rows = new ArrayList<>();

        for (int y = 2; y <= 5; y++) {
            List<Block> row = new ArrayList<>();
            for (int x = 0; x < grid.getWidth(); x++) {
                row.add(grid.peek(x, y));
            }
            rows.add(row);
        }

        return rows;
    }




    @Test
    void clearingMiddleRow_doesNotAffectRowsBelow() {
        GameState state = new GameState(5, 6);
        Grid grid = state.getGrid();

        for (int x = 0; x < grid.getWidth(); x++) {
            grid.placeBlock(
                    x, 1,
                    new Block(new Coord(0, 0), BlockSetType.I)
            );
        }


        Block b1 = new Block(new Coord(0, 0), BlockSetType.O);
        Block b2 = new Block(new Coord(0, 0), BlockSetType.T);

        grid.placeBlock(0, 3, b1);
        grid.placeBlock(1, 4, b2);

        var before = snapshotRows(grid);


        RowClearEligibilityRule rule = new RowClearEligibilityRule(state);
        FrameInput input = new FrameInput();
        RuleContext ctx = new RuleContext(input);


        rule.apply(state, ctx);

        var after = snapshotRows(grid);


        assertEquals(
                before,
                after,
                "rows below cleared row must remain unchanged"
        );
    }




}


