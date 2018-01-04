package genetic;

import game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {

    private int populationSize = 20;
    private double numberOfGenerations = 50;
    private double crossoverProbability = 0.8;
    private double mutationProbability = 0.0001;
    private List<Chromosome> population = new ArrayList<>();
    private Chromosome bestChromosome = new Chromosome();

    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        System.out.println(geneticAlgorithm.buildStrategy().getGenes());
    }

    public Chromosome buildStrategy(){
        runGeneticAlgorithm();
        bestChromosome.resetScore();
        return bestChromosome;
    }

    public void runGeneticAlgorithm() {
        initializePopulationRandomly();
        for (int generation = 0; generation < numberOfGenerations; generation++) {
            population = rouletteWheelPool();
//            applyCrossover();
            applyMutation();
        }
    }

    private void applyMutation() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (Chromosome chromosome: population) {
            double randomNumber = random.nextDouble(0, 1);
            if (randomNumber < this.mutationProbability) {
                chromosome.mutate(this.mutationProbability);
            }
        }
    }

    private void applyCrossover() {
        // TODO implement crossover
    }

    private void initializePopulationRandomly() {
        for (int i = 0; i< this.populationSize; i++) {
            population.add(new Chromosome());
        }
    }

    private List<Chromosome> rouletteWheelPool() {
        List<Chromosome> selectedChromosomes = new ArrayList<>();
        List<Long> populationFitness = getPopulationFitnessList(population);
        List<Double> mergedSelectionProbability = getMergedSelectionProbability(populationFitness);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int tryNumber = 0; tryNumber < population.size(); tryNumber++) {
            double randomNumber = random.nextDouble(0, 1);
            for (int chromosomeIndex = 0; chromosomeIndex < population.size(); chromosomeIndex++) {
                if (mergedSelectionProbability.get(chromosomeIndex) < randomNumber && randomNumber <= mergedSelectionProbability.get(chromosomeIndex + 1)) {
                    selectedChromosomes.add(population.get(chromosomeIndex));
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
    public List<Double> getMergedSelectionProbability(List<Long> populationFitness) {
        List<Double> mergedSelectionProbability = new ArrayList<>(populationFitness.size());
        List<Double> individualSelectionProbability = getIndividualSelectionProbability(populationFitness);
        mergedSelectionProbability.add(0.0);
        for (int i = 0; i < populationFitness.size(); i++) {
            mergedSelectionProbability.add(mergedSelectionProbability.get(i) + individualSelectionProbability.get(i));
        }
        return mergedSelectionProbability;
    }

    public List<Double> getIndividualSelectionProbability(List<Long> populationFitness) {
        double totalFitness = getTotalFitness(populationFitness);
        List<Double> individualSelectionProbability = new ArrayList<>(populationSize);
        for (int i = 0; i < populationFitness.size(); i++) {
            individualSelectionProbability.add(populationFitness.get(i) / totalFitness);
        }
        return individualSelectionProbability;
    }

    public double getTotalFitness(List<Long> populationFitness) {
        double result = 0;
        for (int i = 0; i < populationFitness.size(); i++) {
            result += populationFitness.get(i);
        }
        return result;
    }

    private List<Long> getPopulationFitnessList(List<Chromosome> population) {

        long max_score = 0;
        List<Long> fitnessList = new ArrayList<>();

        List<Player> players = new ArrayList<>();
        players.addAll(population);
        Tournament tournament = new Tournament(players);

        tournament.playTournament();

        for (Player player:population) {
            if (player.getScore() > max_score){
                max_score = player.getScore();
                bestChromosome = (Chromosome) player;
            }
            fitnessList.add(player.getScore());
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
}
