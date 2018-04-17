package player;

/**
 * Cooperation can be seen as the act of helping the other player.
 * The greater the payoff, the better.
 */
public enum Payoff {

    Temptation(5),     // Temptation to defect: player defects, the other cooperates
    SuckerPayoff(0),   // Sucker's payoff: player cooperates, the other defects
    Reward(3),         // Reward for mutual cooperation
    Punishment(1);     // Punishment for mutual defection

    private long scoreValue;

    Payoff(long value) {
        this.scoreValue = value;
    }

    public long getScoreValue() {
        return this.scoreValue;
    }
}
