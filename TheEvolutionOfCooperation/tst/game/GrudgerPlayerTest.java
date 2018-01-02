package game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GrudgerPlayerTest {

    private GrudgerPlayer player;

    @Before
    public void setUp() {
        player = new GrudgerPlayer();
    }

    @After
    public void tearDown() {
        player = null;
    }

    @Test
    public void WHEN_choosing_first_action_THEN_cooperate() {
        assertEquals(Action.Cooperate, player.chooseAction());
    }

    @Test
    public void GIVEN_opponent_last_action_is_defect_WHEN_making_action_THEN_defect() {
        player.registerOpponentActionToHistory(Action.Defect);
        assertEquals(Action.Defect, player.chooseAction());
    }

    @Test
    public void GIVEN_opponent_never_defected_WHEN_making_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(Action.Cooperate, player.chooseAction());
    }

    @Test
    public void GIVEN_opponent_last_match_action_is_cooperate_but_defected_before_WHEN_making_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Defect);
        player.registerActionToHistory(player.chooseAction());
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(Action.Defect, player.chooseAction());
    }


    @Test
    public void test_getPlayerType() {
        String expected = "Grudger";
        assertEquals(expected, player.getPlayerType());
    }

}