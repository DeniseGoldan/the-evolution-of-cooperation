package player;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class PlayerTest {

    Player player = new Player() {
        @Override
        public Action chooseAction(long iteration) {
            return null;
        }

        @Override
        public String getPlayerType() {
            return "test";
        }
    };

    @Test
    public void GIVEN_a_match_reward_THEN_score_gets_updated() {
        int expected = 10;
        player.updateScore(expected);
        assertEquals(expected, player.getScore());
    }

    @Test
    public void test_getScore() throws Exception {
        int expected = 10;
        player.updateScore(expected);
        assertEquals(expected, player.getScore());
    }

    @Test
    public void WHEN_calling_score_getter_after_player_initialization_THEN_the_result_is_0() throws Exception {
        assertEquals(0, player.getScore());
    }

    @Test
    public void player_action_gets_registered_in_his_own_history_and_player_can_access_it() {
        Action expected = Action.Cooperate;
        player.registerActionToHistory(Action.Cooperate);
        assertEquals(expected, player.getActionHistory().get(0));
        assertEquals(1, player.getActionHistory().size());
    }

    @Test
    public void GIVEN_empty_actionHistory_WHEN_calling_getActionFromLastMatch_THEN_return_null() {
        assertEquals(null, player.getActionFromLastMatch());
    }

    @Test
    public void altering_result_of_player_history_getter_does_not_alter_player_history() {
        player.registerActionToHistory(Action.Cooperate);
        List<Action> historyCopy = player.getActionHistory();
        historyCopy.add(Action.Cooperate);
        assertNotEquals(historyCopy, player.getActionHistory());
    }

    @Test
    public void opponent_action_gets_registered_in_opponent_history_and_player_can_access_it() {
        Action expected = Action.Cooperate;
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(expected, player.getOpponentActionHistory().get(0));
        assertEquals(1, player.getOpponentActionHistory().size());
    }

    @Test
    public void GIVEN_empty_opponentActionHistory_WHEN_calling_getActionFromLastMatch_THEN_return_null() {
        assertEquals(null, player.getOpponentActionFromLastRound());
    }

    @Test
    public void altering_result_of_opponent_history_getter_does_not_alter_opponent_history() {
        player.registerOpponentActionToHistory(Action.Cooperate);
        List<Action> historyCopy = player.getOpponentActionHistory();
        historyCopy.add(Action.Cooperate);
        assertNotEquals(historyCopy, player.getActionHistory());
    }

    @Test
    public void player_can_access_opponent_action_from_last_match() throws Exception {
        Action expected = Action.Defect;
        player.registerOpponentActionToHistory(expected);
        assertEquals(expected, player.getOpponentActionFromLastRound());
    }

    @Test
    public void player_can_access_opponent_actions_from_last_two_matches() throws Exception {
        List<Action> expected= new ArrayList<>();
        expected.add(Action.Defect);
        expected.add(Action.Cooperate);
        player.registerOpponentActionToHistory(Action.Defect);
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertEquals(expected.get(0), player.getOpponentActionsFromLastTwoRounds().get(0));
        assertEquals(expected.get(1), player.getOpponentActionsFromLastTwoRounds().get(1));
    }

    @Test
    public void GIVEN_opponentHistory_size_is_0_WHEN_calling_getOpponentActionsFromLastTwoMatches_THEN_return_empty_list() {
        assertEquals(new ArrayList<Action>(), player.getOpponentActionsFromLastTwoRounds());
    }

    @Test
    public void GIVEN_opponentHistory_size_is_1_WHEN_calling_getOpponentActionsFromLastTwoMatches_THEN_return_empty_list() {
        player.registerOpponentActionToHistory(Action.Defect);
        assertEquals(new ArrayList<Action>(), player.getOpponentActionsFromLastTwoRounds());
    }

    @Test
    public void GIVEN_opponent_defects_two_consecutive_times_THEN_hasTheOpponentDefectedLastTwoMatches_returns_true() throws Exception {
        player.registerOpponentActionToHistory(Action.Defect);
        player.registerOpponentActionToHistory(Action.Defect);
        assertTrue(player.hasTheOpponentDefectedLastTwoMatches());
    }

    @Test
    public void GIVEN_opponent_defects_two_consecutive_times_but_then_cooperates_THEN_hasTheOpponentDefectedLastTwoMatches_returns_false() throws Exception {
        player.registerOpponentActionToHistory(Action.Defect);
        player.registerOpponentActionToHistory(Action.Defect);
        player.registerOpponentActionToHistory(Action.Cooperate);
        assertFalse(player.hasTheOpponentDefectedLastTwoMatches());
    }

}