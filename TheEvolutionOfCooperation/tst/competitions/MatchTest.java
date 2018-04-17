package competitions;

import org.junit.Test;
import player.Payoff;
import player.Player;
import strategies.standard.AlwaysCooperatePlayer;
import strategies.standard.AlwaysDefectPlayer;

import static org.junit.Assert.assertEquals;

public class MatchTest {

    @Test
    public void GIVEN_both_players_cooperate_WHEN_playMatch_THEN_update_score_accordingly() {
        Player firstPlayer = new AlwaysCooperatePlayer();
        Player secondPlayer = new AlwaysCooperatePlayer();
        Match match = new Match(firstPlayer, secondPlayer, 1);
        match.playMatch();
        assertEquals(Payoff.Reward.getScoreValue(), firstPlayer.getScore());
        assertEquals(Payoff.Reward.getScoreValue(), secondPlayer.getScore());
    }

    @Test
    public void GIVEN_first_player_cooperates_and_second_defects_WHEN_playMatch_THEN_update_score_accordingly() {
        Player firstPlayer = new AlwaysCooperatePlayer();
        Player secondPlayer = new AlwaysDefectPlayer();
        Match match = new Match(firstPlayer, secondPlayer, 1);
        match.playMatch();
        assertEquals(Payoff.SuckerPayoff.getScoreValue(), firstPlayer.getScore());
        assertEquals(Payoff.Temptation.getScoreValue(), secondPlayer.getScore());
    }

    @Test
    public void GIVEN_first_defects_and_second_player_cooperates_WHEN_playMatch_THEN_update_score_accordingly() {
        Player firstPlayer = new AlwaysDefectPlayer();
        Player secondPlayer = new AlwaysCooperatePlayer();
        Match match = new Match(firstPlayer, secondPlayer, 1);
        match.playMatch();
        assertEquals(Payoff.Temptation.getScoreValue(), firstPlayer.getScore());
        assertEquals(Payoff.SuckerPayoff.getScoreValue(), secondPlayer.getScore());
    }

    @Test
    public void GIVEN_both_players_defect_WHEN_playMatch_THEN_update_score_accordingly() {
        Player firstPlayer = new AlwaysDefectPlayer();
        Player secondPlayer = new AlwaysDefectPlayer();
        Match match = new Match(firstPlayer, secondPlayer, 1);
        match.playMatch();
        assertEquals(Payoff.Punishment.getScoreValue(), firstPlayer.getScore());
        assertEquals(Payoff.Punishment.getScoreValue(), secondPlayer.getScore());
    }

}