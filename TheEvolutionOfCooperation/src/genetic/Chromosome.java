package genetic;

import java.util.Arrays;
import java.util.Random;

/**
 * The population is a list of "candidate solutions" (chromosomes that store the solution,
 * also known as strategy, in our case). Each chromosome represents an individual of the population.
 * Chromosomes have a fixed length in this case, 64. The gene is a unit of information, stored inside a chromosome.
 */
public class Chromosome {

    private static final int LENGTH = 64;
    private Action[] genes;

    public Chromosome() {
        initializeGenesRandomly();
    }

    public Chromosome(Chromosome original) {
        this.genes = Arrays.copyOf(original.genes, LENGTH);
    }

    private void initializeGenesRandomly() {
        for (int geneIndex = 0; geneIndex < LENGTH; geneIndex ++) {
            Random random = new Random();
            if (random.nextBoolean()) {
                genes[geneIndex] = Action.C;
            } else {
                genes[geneIndex] = Action.D;
            }
        }
    }

}
