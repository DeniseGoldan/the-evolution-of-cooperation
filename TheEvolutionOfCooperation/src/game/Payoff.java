package game;

public enum Payoff {

    Temptation(5),     // Temptation to defect -- player defects, the other cooperates
    SuckerPayoff(0),   // Sucker's payoff -- player cooperates, the other defects
    Reward(3),         // Reward for mutual cooperation
    Punishment(1);     // Punishment for mutual defection

    // Cooperation === "helping the other player"

    // The greater the score, the better

    private long scoreValue;

    Payoff(long value) {
        this.scoreValue = value;
    }

    public long getScoreValue() {
        return this.scoreValue;
    }
}
