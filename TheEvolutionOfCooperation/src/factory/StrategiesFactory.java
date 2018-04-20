package factory;

import org.json.simple.parser.ParseException;
import strategies.genetic.GeneticAlgorithm;

import java.io.IOException;

import static factory.StrategyWriter.populateNewFileWithStrategyData;

public class StrategiesFactory {

    public static void main(String[] args) throws IOException, ParseException {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm()
                .withCrossoverProbability(0.3)
                .withMutationProbability(0.5)
                .withNumberOfGenerations(100)
                .withNumberOfRoundsPerMatch(20)
                .withPopulationSize(25);
        populateNewFileWithStrategyData(geneticAlgorithm);
//        System.out.println(StrategyReader.getStrategyFromJsonFile("src/resources/strategy_1523972359871.json"));
    }
}
