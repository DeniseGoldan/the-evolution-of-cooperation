package game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PavlovPlayerTest {

    private PavlovPlayer player;

    @Before
    public void setUp() {
        player = new PavlovPlayer();
    }

    @After
    public void tearDown() {
        player = null;
    }

    @Test
    public void WHEN_choosing_first_action_THEN_cooperate() {
        assertEquals(player.chooseAction(), Action.Cooperate);
    }

    @Test
    public void GIVEN_payoff_from_last_match_is_reward_WHEN_choosing_action_THEN_repeat_last_action() {
        player.registerActionToHistory(Action.Cooperate);
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(player.chooseAction(), player.getActionFromLastMatch());
        assertEquals(player.getActionFromLastMatch(), Action.Cooperate);
    }

    @Test
    public void GIVEN_payoff_from_last_match_is_temptation_WHEN_choosing_action_THEN_repeat_last_action() {
        player.registerActionToHistory(Action.Defect);
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(player.chooseAction(), player.getActionFromLastMatch());
        assertEquals(player.getActionFromLastMatch(), Action.Defect);
    }

    @Test
    public void test_getPlayerType() {
        String expected = "Pavlov";
        assertEquals(expected, player.getPlayerType());
    }

}