package charts;

import competitions.TournamentWithElimination;
import factory.FilePath;
import factory.PopulationConfigReader;
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
import strategies.genetic.InvestigatedChromosome;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static factory.StrategyReader.getChromosomeWithStrategyFromJsonFile;
import static strategies.genetic.InvestigatedChromosome.CHROMOSOME_UNDER_TEST_FILE_PATH;

public class TournamentEvolutionLineChart extends Application {

    private static final String CHART_TITLE = "Tournament Evolution";
    private static final String APPLICATION_TITLE = "Line Chart";
    private static final String X_AXIS_LABEL = "Round number";
    private static final String Y_AXIS_LABEL = "Number of players";
    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;
    private static final int PERCENT_OF_PLAYERS_TO_ELIMINATE = 25;
    private static final int NUMBER_OF_ROUNDS_PER_MATCH = 10;
    private static final int X_AXIS_UPPER_BOUND = 10;
    private static final int Y_AXIS_UPPER_BOUND = 30;
    private static final int X_AXIS_TICK_UNIT = 1;
    private static final int Y_AXIS_TICK_UNIT = 5;

    public static void main(final String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(final Stage stage) throws IOException, ParseException {

        stage.setTitle(APPLICATION_TITLE);
        Scene scene = buildLineChartScene();
        stage.setScene(scene);
        saveLineChartSceneAsPng(scene);
        stage.show();

    }

    @SuppressWarnings("unchecked")
    private Scene buildLineChartScene() throws IOException, ParseException {

        List<Player> players = buildTournamentPlayersList();
        TournamentWithElimination tournament = new TournamentWithElimination(players, PERCENT_OF_PLAYERS_TO_ELIMINATE, NUMBER_OF_ROUNDS_PER_MATCH);
        Map<String, XYChart.Series> seriesPerPlayer = tournament.playTournamentAndGenerateSets();

        final LineChart<Number, Number> lineChart = getNewLineChartWithDefaultAxisLabels();
        lineChart.setTitle(CHART_TITLE);
        Scene scene = new Scene(lineChart, WIDTH, HEIGHT);

        for (String playerType : seriesPerPlayer.keySet()) {
            lineChart.getData().add(seriesPerPlayer.get(playerType));
            System.out.println("something");
        }

        return scene;
    }

    private LineChart<Number, Number> getNewLineChartWithDefaultAxisLabels() {
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

    private List<Player> buildTournamentPlayersList() throws IOException, ParseException {

        List<Player> players = PopulationConfigReader.getPlayersFromConfigFile(FilePath.TestingPhase.getPath());

        Chromosome a = getChromosomeWithStrategyFromJsonFile(CHROMOSOME_UNDER_TEST_FILE_PATH);
        Chromosome b = getChromosomeWithStrategyFromJsonFile(CHROMOSOME_UNDER_TEST_FILE_PATH);
        Chromosome c = getChromosomeWithStrategyFromJsonFile(CHROMOSOME_UNDER_TEST_FILE_PATH);
        Chromosome d = getChromosomeWithStrategyFromJsonFile(CHROMOSOME_UNDER_TEST_FILE_PATH);
        Chromosome e = getChromosomeWithStrategyFromJsonFile(CHROMOSOME_UNDER_TEST_FILE_PATH);
        Chromosome f = getChromosomeWithStrategyFromJsonFile(CHROMOSOME_UNDER_TEST_FILE_PATH);

        players.add(a);
        players.add(b);
        players.add(c);
        players.add(d);
        players.add(e);
        players.add(f);

        return players;
    }

    private void saveLineChartSceneAsPng(final Scene scene) throws IOException {
        WritableImage image = scene.snapshot(null);
        File pngFile = createPngFile();
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", pngFile);
    }

    private File createPngFile() throws IOException {
        String root = "src/resources/from.epoch."
                + InvestigatedChromosome.STRATEGY_CREATION_EPOCH_STRING
                + "/";
        String currentEpochString = String.valueOf(System.currentTimeMillis());
        String path = root + "chart.from.epoch." + currentEpochString + ".png";
        File newFile = new File(path);
        newFile.createNewFile();
        return newFile;
    }

}