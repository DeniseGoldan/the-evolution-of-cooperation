package strategies.standard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import strategies.player.Action;
import strategies.standard.PavlovPlayer;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PavlovPlayerTest {

    private PavlovPlayer player = new PavlovPlayer();

    @Test
    public void WHEN_choosing_first_action_THEN_cooperate() {
        Assert.assertEquals(player.chooseAction(0), Action.Cooperate);
    }

    @Test
    public void GIVEN_payoff_from_last_match_is_reward_WHEN_choosing_action_THEN_repeat_last_action() {
        player.registerActionToHistory(Action.Cooperate);
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(player.chooseAction(1), player.getActionFromLastMatch());
        assertEquals(player.getActionFromLastMatch(), Action.Cooperate);
    }

    @Test
    public void GIVEN_payoff_from_last_match_is_temptation_WHEN_choosing_action_THEN_repeat_last_action() {
        player.registerActionToHistory(Action.Defect);
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(player.chooseAction(1), player.getActionFromLastMatch());
        assertEquals(player.getActionFromLastMatch(), Action.Defect);
    }

    @Test
    public void test_getPlayerType() {
        String expected = "Pavlov";
        assertEquals(expected, player.getPlayerType());
    }

}