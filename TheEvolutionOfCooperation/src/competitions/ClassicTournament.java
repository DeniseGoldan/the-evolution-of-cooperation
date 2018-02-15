package competitions;

import strategies.player.Player;

import java.util.List;

/**
 * This represents a tournament composed of a fixed number of rounds. Players are not eliminated even
 * though they score low. The score is added between rounds. Before the initial round, all scores are
 * being set to their initial value, so that all players have the same staring point.
 *
 * The classic Tournament is used to train the chromosomes for the genetic algorithm.
 */
public class ClassicTournament extends Tournament {

    public ClassicTournament(List<Player> players, long numberOfRoundsPerMatch) {
        super(players, numberOfRoundsPerMatch);
    }

    @Override
    public void playTournament() {
        resetScoreAndNumberOfMatchesCounter();
        playAllPlayersCombinations();
    }

}
