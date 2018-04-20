package competitions;

import factory.StrategyReader;
import javafx.scene.chart.XYChart;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import strategies.standard.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentWithElimination extends Tournament {

    private int numberOfPlayersToEliminate;
    private final int MAX_ROUNDS = 10;
    private final Logger logger = LoggerFactory.getLogger(TournamentWithElimination.class);

    public TournamentWithElimination(List<Player> players, int percentOfPlayersToEliminate, int numberOfRoundsPerMatch) {
        super(players, numberOfRoundsPerMatch);
        assert percentOfPlayersToEliminate > 0 && percentOfPlayersToEliminate < 100;
        assert numberOfRoundsPerMatch > 0;
        this.numberOfPlayersToEliminate = percentOfPlayersToEliminate * players.size() / 100;
    }

    public static void main(String[] args) throws IOException, ParseException {

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            players.add(new TitForTatPlayer());
        }

        for (int i = 0; i < 3; i++) {
            players.add(new AlwaysCooperatePlayer());
        }

        for (int i = 0; i < 20; i++) {
            players.add(new AlwaysDefectPlayer());
        }

        TournamentWithElimination tournament = new TournamentWithElimination(players, 25, 5);
        tournament.playTournament();
    }


    @Override
    public void playTournament() throws IOException, ParseException {
        resetScoreAndNumberOfMatchesCounter();
        int currentRoundNumber = 0;

        while (!areAllPlayersOfTheSameType() && currentRoundNumber < MAX_ROUNDS) {

            logger.info("");
            logger.info("Round #" + currentRoundNumber + " will start with:");
            logPlayerTypeCounter();

            resetScoreAndPlayAllPlayersCombinations();

            for (Player player : players) {
                logger.info(player.toString() +"  has a score of  " + player.getScore() + " points. ");
            }

            reshapePopulation();
            currentRoundNumber++;

        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, XYChart.Series> playTournamentAndGenerateSets() throws IOException, ParseException {

        resetScoreAndNumberOfMatchesCounter();
        int currentRoundNumber = 0;

        Map<String, XYChart.Series> seriesPerPlayer = new HashMap<>();
        for (Player player : players) {
            String currentPlayerType = player.getPlayerType();
            if (!seriesPerPlayer.containsKey(currentPlayerType)) {
                XYChart.Series currentPlayerTypeSeries = new XYChart.Series();
                currentPlayerTypeSeries.setName(currentPlayerType);
                seriesPerPlayer.put(currentPlayerType, currentPlayerTypeSeries);
            }
        }

        while (!areAllPlayersOfTheSameType() && currentRoundNumber < MAX_ROUNDS) {
            logger.info("Round number " + currentRoundNumber + ".");
            Map<String, Integer> typeCounter = getNumberOfPlayersPerType();
            for (String playerType : typeCounter.keySet()) {
                seriesPerPlayer.get(playerType).getData().add(new XYChart.Data(currentRoundNumber, typeCounter.get(playerType)));
                logger.info("There are " + typeCounter.get(playerType) + " players of type \"" + playerType + "\".");
            }
            resetScoreAndPlayAllPlayersCombinations();
            for (Player player : players) {
                System.out.println(player.getPlayerType()
                        + "  has a score of  "
                        + player.getScore()
                        + " points. "
                );
            }
            reshapePopulation();
            currentRoundNumber++;
        }

        logger.info("Round number " + currentRoundNumber + ".");
        Map<String, Integer> typeCounter = getNumberOfPlayersPerType();
        for (String playerType : typeCounter.keySet()) {
            seriesPerPlayer.get(playerType).getData().add(new XYChart.Data(currentRoundNumber, typeCounter.get(playerType)));
            logger.info("There are " + typeCounter.get(playerType) + " players of type \"" + playerType + "\".");
        }
        for (Player player : players) {
            System.out.println(player.getPlayerType()
                    + "  has a score of  "
                    + player.getScore()
                    + " points. "
            );
        }
        return seriesPerPlayer;
    }

    /**
     * Eliminate worst percentOfPlayersToEliminate players and complete the population by
     * duplicating the best percentOfPlayersToEliminate players.s
     */
    private void reshapePopulation() throws IOException, ParseException {
        players.sort((o1, o2) -> (-1) * Long.compare(o1.getScore(), o2.getScore()));
        List<Player> newGeneration = new ArrayList<>();
        for (int i = 0; i < players.size() - numberOfPlayersToEliminate; i++) {
            Player player = players.get(i);
            String type = player.getPlayerType();
            newGeneration.add(getNewPlayerOfType(type));
        }
        for (int i = 0; i < numberOfPlayersToEliminate; i++) {
            Player player = players.get(i);
            String type = player.getPlayerType();
            newGeneration.add(getNewPlayerOfType(type));
        }
        this.players = newGeneration;
    }

    private boolean areAllPlayersOfTheSameType() {
        String firstPlayerType = players.get(0).getPlayerType();
        for (Player player : players) {
            if (!player.getPlayerType().equals(firstPlayerType)) {
                return false;
            }
        }
        return true;
    }

    private Player getNewPlayerOfType(String type) throws IOException, ParseException {
        Player playerToAdd = null;
        switch(type){
            case "Tit-For-Tat": playerToAdd = new TitForTatPlayer(); break;
            case "Always Defect": playerToAdd = new AlwaysDefectPlayer(); break;
            case "Always Cooperate": playerToAdd = new AlwaysCooperatePlayer(); break;
            case "Grudger": playerToAdd = new GrudgerPlayer(); break;
            case "Suspicious Tit-For-Tat": playerToAdd = new SuspiciousTitForTatPlayer(); break;
            case "Tit-For-Two-Tats": playerToAdd = new TitForTwoTatsPlayer(); break;
            case "Random": playerToAdd = new RandomPlayer(); break;
            case "Chromosome": playerToAdd = StrategyReader.getChromosomeWithStrategyFromJsonFile("src/resources/chromosome_strategies/strategy_1524227692965.json"); break;
            default: throw new RuntimeException("This player type is not registered!");
        }
        return playerToAdd;
    }

    private void logPlayerTypeCounter() {
        Map<String, Integer> typeCounter = getNumberOfPlayersPerType();
        for (String type : typeCounter.keySet()) {
            logger.info(type + " count = " + typeCounter.get(type));
        }
    }

    private Map<String, Integer> getNumberOfPlayersPerType() {
        Map<String, Integer> typeCounter = new HashMap<>();
        for (Player player : players) {
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

}
