package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentWithElimination extends Tournament {

    private int numberOfPlayersToEliminate;
    private int maximumNumberOfRounds;

    public TournamentWithElimination(List<Player> players, int percentOfPlayersToEliminate, int numberOfRounds) {
        super(players);
        assert percentOfPlayersToEliminate > 0 && percentOfPlayersToEliminate < 100;
        assert numberOfRounds > 0;
        this.numberOfPlayersToEliminate = percentOfPlayersToEliminate * players.size() / 100;
        this.maximumNumberOfRounds = numberOfRounds;
    }

    @Override
    public void playTournament() {
        resetPlayerScoreToInitialValue();
        int currentRoundNumber = 0;
        while(currentRoundNumber < maximumNumberOfRounds && !areAllPlayersOfTheSameType()) {
            printPlayerTypeCounter();
            System.out.println("Round number:" + currentRoundNumber +".");
            playAllPossibleCombinations();
            reshapePopulation();
            currentRoundNumber ++;
        }
    }

    /**
     * Eliminate worst percentOfPlayersToEliminate players, and complete the population by
     * duplicating the best percentOfPlayersToEliminate.
     */
    private void reshapePopulation() {
        players.sort((o1, o2) -> (-1) * Long.compare(o2.getScore(), o1.getScore()));
        List<Player> newGeneration;
        newGeneration = players.subList(0, players.size()-numberOfPlayersToEliminate);
        newGeneration.addAll(players.subList(0,numberOfPlayersToEliminate));
        this.players = newGeneration;
    }

    private Map<String, Integer> getNumberOfPlayersPerType() {
        Map<String, Integer> typeCounter = new HashMap<>();
        for(Player player : players) {
            String currentPlayerType = player.getPlayerType();
            if (!typeCounter.containsKey(currentPlayerType)) {
                typeCounter.put(currentPlayerType, 1);
            } else {
                int oldValue = typeCounter.get(currentPlayerType);
                typeCounter.put(currentPlayerType, ++oldValue);
            }
        }
        return typeCounter;
    }

    public void printPlayerTypeCounter() {
        Map<String, Integer> typeCounter = getNumberOfPlayersPerType();
        for (String type: typeCounter.keySet()){
            String key = type;
            Integer value = typeCounter.get(key);
            System.out.println(key + " count = " + value);
        }
    }

    private boolean areAllPlayersOfTheSameType() {
        String firstPlayerType = players.get(0).getPlayerType();
        for(Player player : players) {
            if (!player.getPlayerType().equals(firstPlayerType)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        List<Player> players = new ArrayList<>();
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new TitForTatPlayer());
        players.add(new AlwaysDefectPlayer());
        players.add(new SuspiciousTitForTatPlayer());

        TournamentWithElimination tournament = new TournamentWithElimination(
                        players,
                        25,
                        Integer.MAX_VALUE
        );
        tournament.playTournament();

        for (Player player: tournament.players){
            System.out.println(player.getPlayerType()
                    + "  has a score of  "
                    + player.getScore()
                    + " points. "
            );
        }
    }

}
