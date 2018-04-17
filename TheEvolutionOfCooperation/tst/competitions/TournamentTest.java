package competitions;

import org.junit.Before;
import org.junit.Test;
import player.Player;
import strategies.standard.AlwaysCooperatePlayer;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TournamentTest {

    private Tournament tournament;

    @Before
    public void setUp() {
        List<Player> players = new ArrayList<>();
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        tournament = new Tournament(players, 10) {
            @Override
            public void playTournament() { }
        };
    }

    @Test
    public void WHEN_playing_all_combinations_THEN_number_of_matches_is_the_number_of_combinations() {
        tournament.resetScoreAndNumberOfMatchesCounter();
        tournament.playAllPlayersCombinations();
        long numberOfPlayers = tournament.getPlayers().size();
        long expected;
        if (numberOfPlayers % 2 == 0) {
            expected = numberOfPlayers / 2 * (numberOfPlayers - 1);
        } else {
            expected = (numberOfPlayers - 1) / 2 * numberOfPlayers;
        }
        assertEquals(expected, tournament.getNumberOfMatchesPlayed());
    }


    @Test
    public void WHEN_resetScoreAndNumberOfMatches_THEN_reset_score(){
        tournament.playAllPlayersCombinations();
        tournament.resetScoreAndNumberOfMatchesCounter();
        for (Player player : tournament.getPlayers()) {
            assertEquals(0, player.getScore());
        }
    }

    @Test
    public void WHEN_resetScoreAndNumberOfMatches_THEN_reset_match_counter(){
        tournament.playAllPlayersCombinations();
        tournament.resetScoreAndNumberOfMatchesCounter();
        assertEquals(0, tournament.getNumberOfMatchesPlayed());
    }

}