package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.io.IOException;

public class TournamentEvolutionLineChart extends ApplicationFrame {

    private static final String CHART_TITLE = "Tournament Evolution";
    private static final String APPLICATION_TITLE = "Line Chart";
    private static final String CATEGORY_AXIS_LABEL = "Round number";
    private static final String VALUE_AXIS_LABEL = "Number of players";
    private static final int WIDTH = 550;
    private static final int HEIGHT = 350;

    public TournamentEvolutionLineChart(DefaultCategoryDataset dataset) throws IOException {

        super(APPLICATION_TITLE);

        JFreeChart lineChart = ChartFactory.createLineChart(
                CHART_TITLE,
                CATEGORY_AXIS_LABEL,
                VALUE_AXIS_LABEL,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
        setContentPane(chartPanel);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
        File file = createNewJpegFile();
        ChartUtilities.saveChartAsJPEG(file, lineChart, WIDTH + 10, HEIGHT + 10);
    }

    private static File createNewJpegFile() throws IOException {
        String path = "src/resources/line_charts/chart_" + System.currentTimeMillis() + ".jpeg";
        File newFile = new File(path);
        newFile.createNewFile();
        return newFile;
    }

}