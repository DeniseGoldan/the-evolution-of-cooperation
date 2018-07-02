package charts;

import competitions.TournamentWithElimination;
import factory.FilePath;
import factory.PopulationConfigReader;
import factory.StrategyReader;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import player.Player;
import strategies.genetic.Chromosome;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static factory.StrategyReader.getChromosomeWithStrategyFromJsonFile;
import static charts.InvestigatedChromosome.CHROMOSOME_UNDER_TEST_FILE_PATH;

/**
 * Each chart corresponds to a scenario in which a population of players compete
 * in a tournament with elimination.
 *
 * @see competitions.TournamentWithElimination
 */
public class TournamentEvolutionLineChart extends Application {

    private final LineChartConfigurator configurator = new LineChartConfigurator();

    public static void main(final String[] args) throws IOException, ParseException {
        launch(args);
        StrategyReader.printStrategyAndAssociatedHistory(CHROMOSOME_UNDER_TEST_FILE_PATH);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(final Stage stage) throws IOException, ParseException {

        stage.setTitle(configurator.APPLICATION_TITLE);
        Scene scene = buildLineChartScene();
        stage.setScene(scene);
        saveLineChartSceneAsPng(scene);
        stage.show();

    }

    @SuppressWarnings("unchecked")
    private Scene buildLineChartScene() throws IOException, ParseException {

        TournamentWithElimination tournament = new TournamentWithElimination(
                buildTournamentPlayersList(),
                configurator.PERCENT_OF_PLAYERS_TO_ELIMINATE,
                configurator.NUMBER_OF_ROUNDS_PER_MATCH);

        Map<String, XYChart.Series> seriesPerPlayer = tournament.playTournamentAndGenerateSets();

        final LineChart<Number, Number> lineChart = configurator.buildDefaultLineChart();

        Scene scene = new Scene(lineChart, configurator.WIDTH, configurator.HEIGHT);
        for (String playerType : seriesPerPlayer.keySet()) {
            lineChart.getData().add(seriesPerPlayer.get(playerType));
        }

        return scene;
    }

    /**
     * Each tournament with elimination is played by a number of different strategies.
     * For example, you may have 3 Tit-for-Tat players, 10 Always-Defect players and
     * 2 Chromosomes of the same type at the beginning of a tournament.
     *
     * @return a list of players whose evolution we want to save in a png file.
     * It should contain at least one Chromosome.
     * @throws IOException    can be thrown by {@link factory.PopulationConfigReader#getPlayersFromConfigFile(String) getPlayersFromConfigFile}
     * @throws ParseException can be thrown by {@link factory.PopulationConfigReader#getPlayersFromConfigFile(String) getPlayersFromConfigFile}
     */
    private List<Player> buildTournamentPlayersList() throws IOException, ParseException {

        List<Player> players = PopulationConfigReader.getPlayersFromConfigFile(FilePath.TestingPhase.getPath());

        Chromosome chromosomeForTournament;
        for (int i = 0; i < 5; i++) {
            chromosomeForTournament = getChromosomeWithStrategyFromJsonFile(CHROMOSOME_UNDER_TEST_FILE_PATH);
            players.add(chromosomeForTournament);
        }

        return players;
    }

    /**
     * @param scene represents the container for content in a scene graph
     * @throws IOException can be thrown by the call to createPngFile -
     * @see charts.TournamentEvolutionLineChart#createPngFile()
     */
    private void saveLineChartSceneAsPng(final Scene scene) throws IOException {
        WritableImage image = scene.snapshot(null);
        File pngFile = createPngFile();
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", pngFile);
    }

    /**
     * Each .png file corresponds to a chart that is a visualization of how each type of strategy
     * performs in the tournament (how the number of players of each type has changed each round).
     * This project is mostly interested in how well a certain chromosome performs, so each chart
     * whose tournament contains a certain chromosome should be saved in the directory that contains the
     * encoded chromosome strategy. You can have multiple charts (that correspond to multiple tournaments)
     * for the same chromosome.
     *
     * @return a new File object, whose name is standard.
     * @throws IOException if the file could not be created (troubleshoot: the root directory
     *                     does not exist)
     */
    private File createPngFile() throws IOException {

        String root = "src/resources/from.epoch."
                + InvestigatedChromosome.STRATEGY_CREATION_EPOCH_STRING
                + "/";

        String currentEpochString = String.valueOf(System.currentTimeMillis());
        String path = root + "chart_from_epoch_" + currentEpochString + ".png";
        File newFile = new File(path);
        newFile.createNewFile();
        return newFile;
    }

    private static class LineChartConfigurator {

        static final int NUMBER_OF_ROUNDS_PER_MATCH = 4;

        static final String APPLICATION_TITLE = "Evoluţia turneului cu eliminare";

        static final String X_AXIS_LABEL = "Numărul meciului"
                + " (fiecare meci are "
                + NUMBER_OF_ROUNDS_PER_MATCH
                + " runde)";

        static final String Y_AXIS_LABEL = "Număr de jucători";
        static final int WIDTH = 720;
        static final int HEIGHT = 400;
        static final int PERCENT_OF_PLAYERS_TO_ELIMINATE = 25;
        static final int X_AXIS_UPPER_BOUND = 30;
        static final int Y_AXIS_UPPER_BOUND = 15;
        static final int X_AXIS_TICK_UNIT = 5;
        static final int Y_AXIS_TICK_UNIT = 5;

        /**
         * @return a configured line chart
         */
        private LineChart<Number, Number> buildDefaultLineChart() {

            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();

            xAxis.setLabel(X_AXIS_LABEL);
            yAxis.setLabel(Y_AXIS_LABEL);

            xAxis.setAutoRanging(false);
            yAxis.setAutoRanging(false);

            xAxis.setUpperBound(X_AXIS_UPPER_BOUND);
            yAxis.setUpperBound(Y_AXIS_UPPER_BOUND);

            xAxis.setLowerBound(0);
            yAxis.setLowerBound(0);

            xAxis.setTickUnit(X_AXIS_TICK_UNIT);
            yAxis.setTickUnit(Y_AXIS_TICK_UNIT);

            return new LineChart<>(xAxis, yAxis);

        }

    }

}
