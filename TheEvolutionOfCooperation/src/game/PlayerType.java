package game;

public enum PlayerType {

    TitForTat("Tit-For-Tat"),
    Human("Human");

    private String type;

    PlayerType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
