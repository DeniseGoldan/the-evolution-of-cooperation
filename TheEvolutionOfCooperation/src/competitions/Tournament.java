package competitions;

import player.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Tournament {

    List<Player> players;
    private long numberOfMatchesPlayed;
    private long numberOfRounds;

    Tournament(List<Player> players, long numberOfRounds) {
        assert players != null;
        assert !players.isEmpty();
        assert numberOfRounds > 0;
        this.players = players;
        this.numberOfMatchesPlayed = 0;
        this.numberOfRounds = numberOfRounds;
    }

    public abstract void playTournament();

    void playAllPlayersCombinations() {
        int numberOfPlayers = players.size();
        for (int firstPlayerIndex = 0; firstPlayerIndex < numberOfPlayers; firstPlayerIndex++) {
            for (int secondPlayerIndex = firstPlayerIndex + 1; secondPlayerIndex < numberOfPlayers; secondPlayerIndex++) {
                Match match = new Match(players.get(firstPlayerIndex), players.get(secondPlayerIndex), numberOfRounds);
                match.playMatch();
                numberOfMatchesPlayed++;
            }
        }
    }

    void resetScoreAndNumberOfMatchesCounter() {
        this.players.forEach(Player::resetScore);
        this.numberOfMatchesPlayed = 0;
    }

    long getNumberOfMatchesPlayed() {
        return numberOfMatchesPlayed;
    }

    List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
}
