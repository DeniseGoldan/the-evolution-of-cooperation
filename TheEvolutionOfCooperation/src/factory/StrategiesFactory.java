package factory;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class StrategiesFactory {

    public static void main(String[] args) throws IOException, ParseException {
//        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
//        populateNewFileWithStrategyData(geneticAlgorithm);
        System.out.println(StrategyReader.getStrategyFromJsonFile("src/resources/strategy_1523972359871.json"));
    }
}
