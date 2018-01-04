package genetic;

import game.*;

import java.util.ArrayList;
import java.util.List;

public class Tournament {

    private List<Player> players;

    Tournament(List<Player> players) {
        assert players!=null;
        assert !players.isEmpty();
        this.players = players;
    }

    public void playTournament() {
        resetPlayerScoreToInitialValue();
        int numberOfPlayers = players.size();
        for (int firstPlayerIndex = 0; firstPlayerIndex < numberOfPlayers; firstPlayerIndex++ ) {
            for (int secondPlayerIndex = 0; secondPlayerIndex < numberOfPlayers; secondPlayerIndex++ ) {
                if (firstPlayerIndex != secondPlayerIndex) {
                    Match match = new Match(players.get(firstPlayerIndex), players.get(secondPlayerIndex), 1);
                    match.playMatch();
                }
            }
        }
    }

    private void resetPlayerScoreToInitialValue() {
        for (Player player : this.players) {
            player.resetScore();
        }
    }

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new TitForTatPlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new SuspiciousTitForTatPlayer());
        Tournament tournament = new Tournament(players);
        tournament.playTournament();
        for (Player player: players){
            System.out.println(player.getPlayerType() +"  --  " + player.getScore());
        }
    }

}
