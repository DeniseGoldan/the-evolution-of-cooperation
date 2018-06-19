package factory;

import org.json.simple.parser.ParseException;
import strategies.genetic.GeneticAlgorithm;

import java.io.IOException;

import static factory.StrategyWriter.populateNewFileWithStrategyData;

public class ChromosomeStrategyFactory {

    public static void main(final String[] args) throws IOException, ParseException {

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm()
                .withCrossoverProbability(0.50)
                .withMutationProbability(0.50)
                .withNumberOfGenerations(1000)
                .withNumberOfRoundsPerMatch(100)
                .withPopulationSize(15);
        populateNewFileWithStrategyData(geneticAlgorithm);

    }

}
