package competitions;

import charts.TournamentEvolutionLineChart;
import factory.StrategyReader;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import strategies.genetic.Chromosome;
import strategies.standard.AlwaysDefectPlayer;
import strategies.standard.SuspiciousTitForTatPlayer;
import strategies.standard.TitForTatPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentWithElimination extends Tournament {

    private int numberOfPlayersToEliminate;
    private final int MAX_ROUNDS = 100;
    private final Logger logger = LoggerFactory.getLogger(TournamentWithElimination.class);

    private TournamentWithElimination(List<Player> players, int percentOfPlayersToEliminate, int numberOfRoundsPerMatch) {
        super(players, numberOfRoundsPerMatch);
        assert percentOfPlayersToEliminate > 0 && percentOfPlayersToEliminate < 100;
        assert numberOfRoundsPerMatch > 0;
        this.numberOfPlayersToEliminate = percentOfPlayersToEliminate * players.size() / 100;
    }

    @Override
    public void playTournament() {
        resetScoreAndNumberOfMatchesCounter();
        int currentRoundNumber = 0;

        while (!areAllPlayersOfTheSameType() && currentRoundNumber < MAX_ROUNDS) {
            logger.info("Round #" + currentRoundNumber + ".");
            logPlayerTypeCounter();

            System.out.println();
            playAllPlayersCombinations();

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
    }

    public DefaultCategoryDataset playTournamentAndPopulateDataset() {
        resetScoreAndNumberOfMatchesCounter();
        int currentRoundNumber = 0;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        while (!areAllPlayersOfTheSameType() && currentRoundNumber < MAX_ROUNDS) {
            logger.info("Round number " + currentRoundNumber +".");
            Map<String, Integer> typeCounter = getNumberOfPlayersPerType();
            for (String playerType : typeCounter.keySet()) {
                dataset.addValue((Number) typeCounter.get(playerType), playerType, currentRoundNumber);
                logger.info("There are " + typeCounter.get(playerType) + " players of type \"" + playerType + "\".");
            }
            playAllPlayersCombinations();
            reshapePopulation();
            currentRoundNumber++;
        }
        logger.info("Round number " + currentRoundNumber +".");
        Map<String, Integer> typeCounter = getNumberOfPlayersPerType();
        for (String playerType : typeCounter.keySet()) {
            dataset.addValue((Number) typeCounter.get(playerType), playerType, currentRoundNumber);
            logger.info("There are " + typeCounter.get(playerType) + " players of type \"" + playerType + "\".");
        }
        return dataset;
    }

    /**
     * Eliminate worst percentOfPlayersToEliminate players and complete the population by
     * duplicating the best percentOfPlayersToEliminate players.
     */
    private void reshapePopulation() {
        players.sort((o1, o2) -> (-1) * Long.compare(o1.getScore(), o2.getScore()));
        List<Player> newGeneration = players.subList(0, players.size() - numberOfPlayersToEliminate);
        newGeneration.addAll(players.subList(0, numberOfPlayersToEliminate));
        this.players = newGeneration;
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

    private void logPlayerTypeCounter() {
        Map<String, Integer> typeCounter = getNumberOfPlayersPerType();
        for (String type : typeCounter.keySet()) {
            String key = type;
            Integer value = typeCounter.get(key);
            logger.info(key + " count = " + value);
        }
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

    public static void main(String[] args) throws IOException, ParseException {

        List<Player> players = new ArrayList<>();
        players.add(new TitForTatPlayer());
        players.add(new TitForTatPlayer());
        players.add(new TitForTatPlayer());
        players.add(new TitForTatPlayer());
        players.add(new TitForTatPlayer());
        players.add(new AlwaysDefectPlayer());
        players.add(new AlwaysDefectPlayer());
        players.add(new SuspiciousTitForTatPlayer());
        Chromosome fromFile = StrategyReader.getChromosomeWithStrategyFromJsonFile("src/resources/chromosome_strategies/strategy_1523972359871.json");
        fromFile.resetScore();
        players.add(fromFile);
        players.add(fromFile);

        TournamentWithElimination tournament = new TournamentWithElimination(players, 25, 15);
        DefaultCategoryDataset dataset = tournament.playTournamentAndPopulateDataset();
        TournamentEvolutionLineChart chart = new TournamentEvolutionLineChart(dataset);

    }

}
