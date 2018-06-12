package strategies.genetic;

import competitions.ClassicTournament;
import factory.FilePath;
import factory.PopulationConfigReader;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Action;
import player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class GeneticAlgorithm {

    private final Logger logger = LoggerFactory.getLogger(GeneticAlgorithm.class);
    private int populationSize = 14;
    private int numberOfGenerations = 15;
    private double crossoverProbability = 0.3;
    private double mutationProbability = 0.25;
    private List<Chromosome> population = new ArrayList<>();
    private Chromosome bestChromosome = new Chromosome();
    private double bestChromosomeMeanFitnessScore = 0;
    private long numberOfRoundsPerMatch = 10;

    private static final int NUMBER_OF_TRIES = 1000;

    public static void main(String[] args) throws IOException, ParseException {
        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        algorithm.buildStrategy().printGenes();
    }

    public Chromosome buildStrategy() throws IOException, ParseException {
        runGeneticAlgorithm();
        bestChromosome.resetScore();
        return bestChromosome;
    }

    /**
     * Given a configuration, restart the search for the best chromosome NUMBER_OF_TRIES times
     */
    private void runGeneticAlgorithm() throws IOException, ParseException {

        for (int tryIndex = 0; tryIndex < NUMBER_OF_TRIES; tryIndex++) {

            initializePopulationRandomly();
            for (int generation = 0; generation < numberOfGenerations; generation++) {
                logger.info("Currently changing genes from generation number " + generation + ".");
                population = rouletteWheelPool();
                applyCrossover();
                applyMutation();
            }

        }

    }

    private void applyMutation() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (Chromosome chromosome : population) {
            double randomNumber = random.nextDouble(0, 1);
            if (randomNumber < this.mutationProbability) {
                chromosome.mutate(this.mutationProbability);
            }
        }
    }

    private void applyCrossover() {
        List<Chromosome> populationAfterCrossover = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        Collections.shuffle(population);

        for (int pairIndex = 0; pairIndex < populationSize - 1; pairIndex += 2) {
            Chromosome firstParentChromosome = population.get(pairIndex);
            Chromosome secondParentChromosome = population.get(pairIndex);
            double randomNumber = random.nextDouble(0, 1);
            if (randomNumber < this.crossoverProbability) {
                List<Chromosome> offsprings = getCrossoverOffsprings(firstParentChromosome, secondParentChromosome);
                populationAfterCrossover.addAll(offsprings);
            } else {
                populationAfterCrossover.add(firstParentChromosome);
                populationAfterCrossover.add(secondParentChromosome);
            }
        }

        if (populationAfterCrossover.size() < population.size()) {
            populationAfterCrossover.add(population.get(populationSize - 1));
        }

        population = populationAfterCrossover;

    }

    private List<Chromosome> getCrossoverOffsprings(Chromosome firstParent, Chromosome secondParent) {

        final int numberOfGenes = firstParent.getNumberOfGenes();
        int cutPointIndex = ThreadLocalRandom.current().nextInt(1, numberOfGenes - 1);

        List<Action> firstOffspringGenes = new ArrayList<>();
        List<Action> secondOffspringGenes = new ArrayList<>();

        for (int i = 0; i < cutPointIndex; i++) {
            firstOffspringGenes.add(firstParent.getGenes().get(i));
            secondOffspringGenes.add(secondParent.getGenes().get(i));
        }

        for (int i = cutPointIndex; i < numberOfGenes; i++) {
            firstOffspringGenes.add(secondParent.getGenes().get(i));
            secondOffspringGenes.add(firstParent.getGenes().get(i));
        }

        List<Chromosome> offsprings = setOffspringsGenes(firstOffspringGenes, secondOffspringGenes);

        return offsprings;
    }

    private List<Chromosome> setOffspringsGenes(List<Action> firstOffspringGenes, List<Action> secondOffspringGenes) {

        Chromosome firstOffspring = new Chromosome();
        firstOffspring.setGenes(firstOffspringGenes);

        Chromosome secondOffspring = new Chromosome();
        secondOffspring.setGenes(secondOffspringGenes);

        List<Chromosome> offsprings = new ArrayList<>();
        offsprings.add(firstOffspring);
        offsprings.add(secondOffspring);

        return offsprings;
    }

    private void initializePopulationRandomly() {
        for (int i = 0; i < this.populationSize; i++) {
            population.add(new Chromosome());
        }
    }

    private List<Chromosome> rouletteWheelPool() throws IOException, ParseException {
        List<Chromosome> selectedChromosomes = new ArrayList<>();
        List<Long> populationFitness = getPopulationFitnessListAndUpdateBestChromosome(population);
        List<Double> mergedSelectionProbability = getMergedSelectionProbability(populationFitness);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int tryNumber = 0; tryNumber < population.size(); tryNumber++) {
            double randomNumber = random.nextDouble(0, 1);
            for (int chromosomeIndex = 0; chromosomeIndex < population.size(); chromosomeIndex++) {
                if (mergedSelectionProbability.get(chromosomeIndex) < randomNumber && randomNumber <= mergedSelectionProbability.get(chromosomeIndex + 1)) {
                    selectedChromosomes.add(new Chromosome(population.get(chromosomeIndex)));
                }
            }
        }
        return selectedChromosomes;
    }

    /**
     * Returns a list of ascending order doubles from 0.0000 to 1.0000.
     * Each pair of consecutive index values represent the endpoints of a interval as in ( q[i] , q[i+1] ].
     * For a population of size N, in order to have N such intervals, I will need a list of N+1 doubles.
     * In order to later on select an individual j, I will randomly choose a double r such that q[j]< r <= q[j+1],
     * where r in [0, 1].
     */
    private List<Double> getMergedSelectionProbability(List<Long> populationFitness) {
        List<Double> mergedSelectionProbability = new ArrayList<>(populationFitness.size());
        List<Double> individualSelectionProbability = getIndividualSelectionProbability(populationFitness);
        mergedSelectionProbability.add(0.0);
        for (int i = 0; i < populationFitness.size(); i++) {
            mergedSelectionProbability.add(mergedSelectionProbability.get(i) + individualSelectionProbability.get(i));
        }
        return mergedSelectionProbability;
    }

    private List<Double> getIndividualSelectionProbability(List<Long> populationFitness) {
        double totalFitness = getTotalFitness(populationFitness);
        List<Double> individualSelectionProbability = new ArrayList<>(populationSize);
        for (Long currentFitnessValue : populationFitness) {
            individualSelectionProbability.add(currentFitnessValue / totalFitness);
        }
        return individualSelectionProbability;
    }

    private double getTotalFitness(List<Long> populationFitness) {
        double result = 0;
        for (Long currentFitnessValue : populationFitness) {
            result += currentFitnessValue;
        }
        return result;
    }

    private List<Long> getPopulationFitnessListAndUpdateBestChromosome(List<Chromosome> population) throws IOException, ParseException {

        List<Long> fitnessList = new ArrayList<>();

        List<Player> enemies = PopulationConfigReader.getPlayersFromConfigFile(FilePath.TrainingPhase.getPath());

        for (Player currentChromosome : population) {

            //logger.info("Preparing classic tournament for the current chromosome...");

            List<Player> classicTournamentPlayers = new ArrayList<>();
            classicTournamentPlayers.addAll(enemies);
            classicTournamentPlayers.add(currentChromosome);
            classicTournamentPlayers.forEach(Player::resetScore);

            ClassicTournament tournament = new ClassicTournament(
                    classicTournamentPlayers,
                    this.numberOfRoundsPerMatch
            );
            tournament.playTournament();

            //logger.info("Finished classic tournament for current chromosome...");

            if (currentChromosome.getScore() > bestChromosomeMeanFitnessScore * numberOfRoundsPerMatch * enemies.size()) {
                bestChromosome = (Chromosome) currentChromosome;
                bestChromosomeMeanFitnessScore =
                        currentChromosome.getScore() * 1.0
                        /numberOfRoundsPerMatch
                        /enemies.size();
                logger.info("Found a better chromosome, with a score of "
                        + bestChromosomeMeanFitnessScore
                        + "...");
            }

            fitnessList.add(currentChromosome.getScore());

//            logger.info("----- Classic tournament results -----");
//            for (Player player : classicTournamentPlayers) {
//                logger.info(" " + player.getScore() + " " + player.getPlayerType());
//            }
//            logger.info("");
        }
        return fitnessList;
    }

    public GeneticAlgorithm withPopulationSize(int desiredPopulationSize) {
        populationSize = desiredPopulationSize;
        return this;
    }

    public GeneticAlgorithm withNumberOfGenerations(int desiredNumberOfGenerations) {
        numberOfGenerations = desiredNumberOfGenerations;
        return this;
    }

    public GeneticAlgorithm withCrossoverProbability(double desiredCrossoverProbability) {
        crossoverProbability = desiredCrossoverProbability;
        return this;
    }

    public GeneticAlgorithm withMutationProbability(double desiredMutationProbability) {
        mutationProbability = desiredMutationProbability;
        return this;
    }

    public GeneticAlgorithm withNumberOfRoundsPerMatch(long desiredNumberOfRoundsPerMatch) {
        numberOfRoundsPerMatch = desiredNumberOfRoundsPerMatch;
        return this;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public double getBestChromosomeMeanFitnessScore() {
        return bestChromosomeMeanFitnessScore;
    }

    public long getNumberOfRoundsPerMatch() {
        return numberOfRoundsPerMatch;
    }

}
