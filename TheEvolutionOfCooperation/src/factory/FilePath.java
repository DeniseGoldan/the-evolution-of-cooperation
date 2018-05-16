package factory;

/**
* Contains paths to the population configurations files.
*/
public enum FilePath {

    /**
     * This is the path to the configuration file used to train the chromosomes.
     * It contains the specification of a population. Each chromosome will
     * have its fitness calculated by finding the score he gets when competing
     * in a Classic Tournament with this population.
     */
    TrainingPhase("src/factory/training.config.json"),

    /**
     * This is the path to the configuration file used to test the chromosomes,
     * and it contains the specifications for the test population. The chosen
     * test chromosomes will play in a Tournament with Elimination with this population.
     */
    TestingPhase("src/factory/testing.config.json");

    /**
     * The file path of the configuration file.
     * */
    private String path;

    FilePath(final String value) { this.path  = value; }

    public String getPath() { return this.path; }

}
