package game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GrudgerPlayerTest {

    private GrudgerPlayer player = new GrudgerPlayer();

    @Test
    public void WHEN_choosing_first_action_THEN_cooperate() {
        assertEquals(Action.Cooperate, player.chooseAction(0));
    }

    @Test
    public void GIVEN_opponent_last_action_is_defect_WHEN_choosing_action_THEN_defect() {
        player.registerOpponentActionToHistory(Action.Defect);
        assertEquals(Action.Defect, player.chooseAction(1));
    }

    @Test
    public void GIVEN_opponent_always_cooperated_WHEN_choosing_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(Action.Cooperate, player.chooseAction(1));
    }

    @Test
    public void GIVEN_opponent_last_match_action_is_cooperate_but_defected_before_WHEN_choosing_action_THEN_cooperate() {
        player.registerOpponentActionToHistory(Action.Defect);
        player.registerActionToHistory(player.chooseAction(1));
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(Action.Defect, player.chooseAction(2));
    }


    @Test
    public void test_getPlayerType() {
        String expected = "Grudger";
        assertEquals(expected, player.getPlayerType());
    }

}