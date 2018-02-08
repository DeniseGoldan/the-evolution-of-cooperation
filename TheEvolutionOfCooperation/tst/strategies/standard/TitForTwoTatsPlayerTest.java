package strategies.standard;

import org.junit.Assert;
import org.junit.Test;
import strategies.player.Action;

import static org.junit.Assert.assertEquals;

public class TitForTwoTatsPlayerTest {

    private TitForTwoTatsPlayer player = new TitForTwoTatsPlayer();

    @Test
    public void WHEN_choosing_first_action_THEN_cooperate() {
        Assert.assertEquals(Action.Cooperate, player.chooseAction(0));
    }

    @Test
    public void GIVEN_opponent_last_match_action_is_cooperate_WHEN_choosing_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(Action.Cooperate, player.chooseAction(1));
    }

    @Test
    public void GIVEN_opponent_last_two_match_actions_are_cooperate_then_defect_WHEN_choosing_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Cooperate);
        player.registerActionToHistory(player.chooseAction(1));
        player.registerOpponentActionToHistory(Action.Defect);
        assertEquals(Action.Cooperate, player.chooseAction(2));
    }

    @Test
    public void GIVEN_opponent_chooses_defect_two_consecutive_times_WHEN_choosing_action_THEN_defect() {
        player.registerOpponentActionToHistory(Action.Defect);
        player.registerActionToHistory(player.chooseAction(1));
        player.registerOpponentActionToHistory(Action.Defect);
        assertEquals(Action.Defect, player.chooseAction(2));
    }

    @Test
    public void GIVEN_opponent_cooperates_two_consecutive_times_then_defects_WHEN_making_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Defect);
        player.registerActionToHistory(player.chooseAction(1));
        player.registerOpponentActionToHistory(Action.Defect);
        player.registerActionToHistory(player.chooseAction(2));
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(Action.Cooperate, player.chooseAction(3));
    }

    @Test
    public void test_getPlayerType() {
        String expected = "Tit-For-Two-Tats";
        assertEquals(expected, player.getPlayerType());
    }

}