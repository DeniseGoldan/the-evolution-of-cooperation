package strategies.standard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import player.Action;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SuspiciousTitForTatPlayerTest {

    private SuspiciousTitForTatPlayer player = new SuspiciousTitForTatPlayer();;

    @Test
    public void WHEN_choosing_first_action_THEN_defect() {
        Assert.assertEquals(Action.Defect, player.chooseAction(0));
    }

    @Test
    public void GIVEN_opponent_last_match_action_is_cooperate_WHEN_making_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(Action.Cooperate, player.chooseAction(1));
    }

    @Test
    public void GIVEN_opponent_last_match_action_is_defect_WHEN_making_action_THEN_defect() {
        player.registerOpponentActionToHistory(Action.Defect);
        assertEquals(Action.Defect, player.chooseAction(1));
    }

    @Test
    public void test_getPlayerType() {
        String expected = "Suspicious Tit-For-Tat";
        assertEquals(expected, player.getPlayerType());
    }


}