package factory;

public enum FilePath {

    TrainingPhase("src/factory/training.config.json"),
    TestingPhase("src/factory/testing.config.json");

    private String path;

    FilePath(String value) { this.path  = value; }

    public String getPath() { return this.path; }

}
