package game;

import java.util.List;

public abstract class Tournament {

    List<Player> players;

    Tournament(List<Player> players) {
        assert players != null;
        assert !players.isEmpty();
        this.players = players;
    }

    public abstract void playTournament();

    void resetPlayerScoreToInitialValue() {
        this.players.forEach(Player::resetScore);
    }

    void playAllPossibleCombinations() {
        int numberOfPlayers = players.size();
        for (int firstPlayerIndex = 0; firstPlayerIndex < numberOfPlayers; firstPlayerIndex++) {
            for (int secondPlayerIndex = 0; secondPlayerIndex < numberOfPlayers; secondPlayerIndex++) {
                if (firstPlayerIndex != secondPlayerIndex) {
                    Match match = new Match(players.get(firstPlayerIndex), players.get(secondPlayerIndex));
                    match.playMatch();
                }
            }
        }
    }

}
