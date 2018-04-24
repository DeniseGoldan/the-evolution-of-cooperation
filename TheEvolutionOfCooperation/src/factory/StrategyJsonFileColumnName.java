package factory;

enum StrategyJsonFileColumnName {

    PopulationSize("population size"),
    NumberOfGenerations("number of generations"),
    CrossoverProbability("crossover probability"),
    MutationProbability("mutation probability"),
    NumberOfRoundsPerMatch("number of rounds per match"),
    FitnessScore("fitness score"),
    EncodedStrategy("encoded strategy");

    private String columnName;

    StrategyJsonFileColumnName(String value) {
        this.columnName = value;
    }

    public String getColumnName() {
        return this.columnName;
    }

}
