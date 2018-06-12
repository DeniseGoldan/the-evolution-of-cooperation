package strategies;

import factory.StrategyReader;
import org.json.simple.parser.ParseException;
import strategies.genetic.Chromosome;
import strategies.genetic.InvestigatedChromosome;

import java.io.IOException;

public class StrategyMeaningExtractor {

    public static void main(String[] args) throws IOException, ParseException {
        StrategyMeaningExtractor.printMeaningOfStrategy();
    }

    static void printMeaningOfStrategy() throws IOException, ParseException {

        StrategyReader strategyReader = new StrategyReader();
        Chromosome chromosomeUnderTest = strategyReader.getChromosomeWithStrategyFromJsonFile(InvestigatedChromosome.CHROMOSOME_UNDER_TEST_FILE_PATH);
        for (int i = 0; i < 71; i++) {
            System.out.println(chromosomeUnderTest.getGenes().get(i)
                    + " " +
                    String.format("%03d", Integer.toBinaryString(i))
                            .replace("0", "C")
                            .replace("1", "D")
            );
        }

    }

}
