package charts;

import competitions.TournamentWithElimination;
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
import strategies.standard.AlwaysCooperatePlayer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TournamentEvolutionLineChart extends Application {

    private static final String CHART_TITLE = "Tournament Evolution";
    private static final String APPLICATION_TITLE = "Line Chart";
    private static final String X_AXIS_LABEL = "Round number";
    private static final String Y_AXIS_LABEL = "Number of players";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int PERCENT_OF_PLAYERS_TO_ELIMINATE = 25;
    private static final int NUMBER_OF_ROUNDS_PER_MATCH = 15;

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage) throws IOException, ParseException {

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
        xAxis.setUpperBound(20);
        yAxis.setUpperBound(15);
        xAxis.setLowerBound(0);
        yAxis.setLowerBound(0);
        xAxis.setTickUnit(1);
        yAxis.setTickUnit(1);
        return new LineChart<>(xAxis, yAxis);
    }

    private List<Player> buildTournamentPlayersList() throws IOException, ParseException {
        List<Player> players = new ArrayList<>();

        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());
        players.add(new AlwaysCooperatePlayer());


        Chromosome firstChromosome = StrategyReader.getChromosomeWithStrategyFromJsonFile("src/resources/chromosome_strategies/strategy_1523972359871.json");
        Chromosome secondChromosome = StrategyReader.getChromosomeWithStrategyFromJsonFile("src/resources/chromosome_strategies/strategy_1523972359871.json");
        Chromosome thirdChromosome = StrategyReader.getChromosomeWithStrategyFromJsonFile("src/resources/chromosome_strategies/strategy_1523972359871.json");
        Chromosome fourthChromosome = StrategyReader.getChromosomeWithStrategyFromJsonFile("src/resources/chromosome_strategies/strategy_1523972359871.json");

        players.add(firstChromosome);
        players.add(secondChromosome);
        players.add(thirdChromosome);
        players.add(fourthChromosome);

        return players;
    }

    private void saveLineChartSceneAsPng(Scene scene) throws IOException {
        WritableImage image = scene.snapshot(null);
        File pngFile = createPngFile();
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", pngFile);
    }

    private static File createPngFile() throws IOException {
        String path = "src/resources/line_charts/chart_" + System.currentTimeMillis() + ".png";
        File newFile = new File(path);
        newFile.createNewFile();
        return newFile;
    }

}