package game;

import genetic.Action;

public abstract class Player {

    private double score;

    Player() {
        this.score = 0;
    }

    public abstract Action getNextMove();

    public abstract String getPlayerType();

    public void incrementScore(double valueToBeAdded) {
        this.score += valueToBeAdded;
    }

    public double getScore() {
        return this.score;
    }
}
