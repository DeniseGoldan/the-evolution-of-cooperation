package game;

public enum Payoff {

    TEMPTATION(5), //Temptation to defect
    SUCKER(0), //Sucker's Payoff
    REWARD(3), //Reward for mutual cooperation
    PUNISHMENT(1); //Punishment for mutual defection

    private double scoreValue;

    Payoff(double value) {
        this.scoreValue = value;
    }

    public double getScoreValue() {
        return this.scoreValue;
    }
}
