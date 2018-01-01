package game;

public enum Payoff {

    TEMPTATION(5),     // Temptation to defect -- player defects, the other cooperates
    SUCKER(0),         // Sucker's payoff -- player cooperates, the other defects
    REWARD(3),         // Reward for mutual cooperation
    PUNISHMENT(1);     // Punishment for mutual defection

    // Cooperation === "helping the other player"

    // The greater the score, the better

    private double scoreValue;

    Payoff(double value) {
        this.scoreValue = value;
    }

    public double getScoreValue() {
        return this.scoreValue;
    }
}
