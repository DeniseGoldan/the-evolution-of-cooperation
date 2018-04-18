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
import strategies.standard.AlwaysDefectPlayer;
import strategies.standard.SuspiciousTitForTatPlayer;
import strategies.standard.TitForTatPlayer;

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
    private static final int HEIGHT = 300;

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage) throws IOException, ParseException {

        stage.setTitle(APPLICATION_TITLE);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(X_AXIS_LABEL);
        yAxis.setLabel(Y_AXIS_LABEL);

        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle(CHART_TITLE);


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
        Map<String, XYChart.Series> seriesPerPlayer = tournament.playTournamentAndGenerateSets();

        Scene scene = new Scene(lineChart, WIDTH, HEIGHT);

        for (String playerType:seriesPerPlayer.keySet()) {
            lineChart.getData().add(seriesPerPlayer.get(playerType));
        }

        stage.setScene(scene);
        saveSceneAsPng(scene);
        stage.show();

    }

    public void saveSceneAsPng(Scene scene) throws IOException {
        WritableImage image = scene.snapshot(null);
        File file = createNewPngFile();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File createNewPngFile() throws IOException {
        String path = "src/resources/line_charts/chart_" + System.currentTimeMillis() + ".png";
        File newFile = new File(path);
        newFile.createNewFile();
        return newFile;
    }



}