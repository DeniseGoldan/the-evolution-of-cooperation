package competitions;

import javafx.scene.chart.XYChart;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import strategies.standard.AlwaysCooperatePlayer;
import strategies.standard.AlwaysDefectPlayer;
import strategies.standard.GrudgerPlayer;
import strategies.standard.TitForTatPlayer;

import java.io.IOException;
import java.util.*;

public class TournamentWithElimination extends Tournament {

    private final int MAX_ROUNDS = 2;
    private final Logger logger = LoggerFactory.getLogger(TournamentWithElimination.class);
    private int numberOfPlayersToEliminate;

    public TournamentWithElimination(List<Player> players, int percentOfPlayersToEliminate, int numberOfRoundsPerMatch) {
        super(players, numberOfRoundsPerMatch);
        assert percentOfPlayersToEliminate > 0 && percentOfPlayersToEliminate < 100;
        assert numberOfRoundsPerMatch > 0;
        this.numberOfPlayersToEliminate = percentOfPlayersToEliminate * players.size() / 100;
    }

    public static void main(String[] args) {

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            players.add(new TitForTatPlayer());
        }

        for (int i = 0; i < 2; i++) {
            players.add(new AlwaysDefectPlayer());
        }

        for (int i = 0; i < 8; i++) {
            players.add(new GrudgerPlayer());
        }

        for (int i = 0; i < 5; i++) {
            players.add(new AlwaysCooperatePlayer());
        }

        TournamentWithElimination tournamentWithElimination = new TournamentWithElimination(
                players, 20, 10
        );
        try {
            tournamentWithElimination.playTournament();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

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
                logger.info(player.toString() + "  has a score of  " + player.getScore() + " points. ");
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
     * duplicating the best percentOfPlayersToEliminate players.
     * The list of players is shuffled so that in case you have to pick from 2 players
     * that have the same score, you make a random choice.
     */
    private void reshapePopulation() throws IOException, ParseException {
        Collections.shuffle(players); // in order to pick randomly one out of two players with the same score
        players.sort((o1, o2) -> (-1) * Long.compare(o1.getScore(), o2.getScore()));
        List<Player> newGeneration = new ArrayList<>();
        for (int i = 0; i < players.size() - numberOfPlayersToEliminate; i++) {
            Player player = players.get(i);
            String type = player.getPlayerType();
            newGeneration.add(Player.getNewPlayerOfType(type));
        }
        for (int i = 0; i < numberOfPlayersToEliminate; i++) {
            Player player = players.get(i);
            String type = player.getPlayerType();
            newGeneration.add(Player.getNewPlayerOfType(type));
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
