package strategies.standard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import strategies.player.Action;
import strategies.standard.AlwaysDefectPlayer;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class AlwaysDefectPlayerTest {

    private AlwaysDefectPlayer player = new AlwaysDefectPlayer();

    @Test
    public void WHEN_choosing_first_action_THEN_defect() {
        Assert.assertEquals(Action.Defect, player.chooseAction(0));
    }

    @Test
    public void GIVEN_last_opponent_game_action_is_cooperate_WHEN_choosing_action_THEN_DEFECT() {
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(Action.Defect, player.chooseAction(1));
    }

    @Test
    public void GIVEN_last_opponent_game_action_is_defect_WHEN_choosing_action_THEN_defect() {
        player.registerOpponentActionToHistory(Action.Defect);
        assertEquals(Action.Defect, player.chooseAction(1));
    }

    @Test
    public void test_getPlayerType() {
        String expected = "Always Defect";
        assertEquals(expected, player.getPlayerType());
    }

}