package factory;

enum StrategyJsonColumnName {

    PopulationSize("population size"),
    NumberOfGenerations("number of generations"),
    CrossoverProbability("crossover probability"),
    MutationProbability("mutation probability"),
    NumberOfRoundsPerMatch("number of rounds per match"),
    MeanFitnessScore("mean of fitness score"),
    EncodedStrategy("encoded strategy"),
    TrainingPopulationConfiguration("players from the classic tournament");

    private String columnName;

    StrategyJsonColumnName(String value) {
        this.columnName = value;
    }

    public String getColumnName() {
        return this.columnName;
    }

}
