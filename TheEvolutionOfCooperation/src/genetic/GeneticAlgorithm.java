package genetic;

public class GeneticAlgorithm {

    private int populationSize = 60;
    private double numberOfGenerations = Double.MAX_VALUE;
    private double fitnessConstant = 0;
    private double crossoverProbability = 0.8;
    private double mutationProbability = 0.0001;

    public GeneticAlgorithm withPopulationSize(int desiredPopulationSize) {
        populationSize = desiredPopulationSize;
        return this;
    }

    public GeneticAlgorithm withNumberOfGenerations(int desiredNumberOfGenerations) {
        numberOfGenerations = desiredNumberOfGenerations;
        return this;
    }

    public GeneticAlgorithm withFitnessConstant(double desiredFitnessConstant) {
        fitnessConstant = desiredFitnessConstant;
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
