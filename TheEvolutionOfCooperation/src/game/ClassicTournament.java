package game;

import java.util.ArrayList;
import java.util.List;

public class ClassicTournament extends Tournament {

    public ClassicTournament(List<Player> players) {
        super(players);
    }

    @Override
    public void playTournament() {
        resetPlayerScoreToInitialValue();
        playAllPossibleCombinations();
    }

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new TitForTatPlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new SuspiciousTitForTatPlayer());
        ClassicTournament tournament = new ClassicTournament(players);
        tournament.playTournament();
        for (Player player: players){
            System.out.println(player.getPlayerType() +"  --  " + player.getScore());
        }
    }

}
