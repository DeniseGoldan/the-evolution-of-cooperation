package factory;

import org.json.simple.parser.ParseException;
import strategies.genetic.GeneticAlgorithm;

import java.io.IOException;

import static factory.StrategyWriter.populateNewFileWithStrategyData;

public class ChromosomeStrategyFactory {

    public static void main(final String[] args) throws IOException, ParseException {

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm()
                .withCrossoverProbability(0.3)
                .withMutationProbability(0.3)
                .withNumberOfGenerations(100)
                .withNumberOfRoundsPerMatch(50)
                .withPopulationSize(25);
        populateNewFileWithStrategyData(geneticAlgorithm);
    }

}
