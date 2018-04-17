package strategies.standard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import player.Action;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class AlwaysCooperatePlayerTest {

    private AlwaysCooperatePlayer player = new AlwaysCooperatePlayer();;

    @Test
    public void WHEN_choosing_first_action_THEN_cooperate() {
        Assert.assertEquals(Action.Cooperate, player.chooseAction(0));
    }

    @Test
    public void GIVEN_last_opponent_game_action_is_cooperate_WHEN_choosing_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(Action.Cooperate, player.chooseAction(0));
    }

    @Test
    public void GIVEN_last_opponent_game_action_is_defect_WHEN_choosing_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Defect);
        assertEquals(Action.Cooperate, player.chooseAction(0));
    }

    @Test
    public void test_getPlayerType() {
        String expected = "Always Cooperate";
        assertEquals(expected, player.getPlayerType());
    }

}