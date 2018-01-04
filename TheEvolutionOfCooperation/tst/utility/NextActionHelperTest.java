package utility;

import game.Action;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NextActionHelperTest {

    @Test
    public void history_of_CC_CC_CC_is_7 () {
        List<Action> history = new ArrayList<>();
        history.add(Action.Cooperate); history.add(Action.Cooperate);
        history.add(Action.Cooperate); history.add(Action.Cooperate);
        history.add(Action.Cooperate); history.add(Action.Cooperate);
        assertEquals(7, NextActionHelper.getGeneIndexForThreeLastMatchesHistory(history));
    }

    @Test
    public void history_of_CC_CC_CD_is_8 () {
        List<Action> history = new ArrayList<>();
        history.add(Action.Cooperate); history.add(Action.Cooperate);
        history.add(Action.Cooperate); history.add(Action.Cooperate);
        history.add(Action.Cooperate); history.add(Action.Defect);
        assertEquals(8, NextActionHelper.getGeneIndexForThreeLastMatchesHistory(history));
    }

    @Test
    public void history_of_CD_DD_DD_is_38 () {
        List<Action> history = new ArrayList<>();
        history.add(Action.Cooperate); history.add(Action.Defect);
        history.add(Action.Defect); history.add(Action.Defect);
        history.add(Action.Defect); history.add(Action.Defect);
        assertEquals(38, NextActionHelper.getGeneIndexForThreeLastMatchesHistory(history));
    }

    @Test
    public void history_of_DD_DD_CD_is_68 () {
        List<Action> history = new ArrayList<>();
        history.add(Action.Defect); history.add(Action.Defect);
        history.add(Action.Defect); history.add(Action.Defect);
        history.add(Action.Cooperate); history.add(Action.Defect);
        assertEquals(68, NextActionHelper.getGeneIndexForThreeLastMatchesHistory(history));
    }

    @Test
    public void history_of_DD_DD_DD_is_70 () {
        List<Action> history = new ArrayList<>();
        history.add(Action.Defect); history.add(Action.Defect);
        history.add(Action.Defect); history.add(Action.Defect);
        history.add(Action.Defect); history.add(Action.Defect);
        assertEquals(70, NextActionHelper.getGeneIndexForThreeLastMatchesHistory(history));
    }

}