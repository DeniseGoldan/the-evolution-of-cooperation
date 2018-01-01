package game;

import genetic.Action;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class Player {

    private double score;
    private Deque<Action> lastThreeActions;
    private Deque<Action> otherPlayerLastThreeActions;

    Player() {
        this.score = 0;
        this.lastThreeActions = new ArrayDeque<>(3);
        this.otherPlayerLastThreeActions = new ArrayDeque<>(3);
    }

    public abstract Action getNextAction();

    public abstract String getPlayerType();

    public void updateScore(double valueToBeAdded) {
        this.score += valueToBeAdded;
    }

    public double getScore() {
        return this.score;
    }

    public void addOwnActionToDeque(Action action) {
        lastThreeActions.add(action);
        if (lastThreeActions.size() > 3) {
            lastThreeActions.pop();
        }
        System.out.println("Player "+ getPlayerType() + " made the following move: \"" + action +"\"" );
        System.out.println(lastThreeActions.toString());
    }

    public Action getLastAction() {
        if (lastThreeActions != null || !lastThreeActions.isEmpty()) {
            return lastThreeActions.peekLast();
        }
        else {
            return null;
        }
    }

    public void addOtherPlayerActionToDeque(Action action) {
        otherPlayerLastThreeActions.add(action);
        if (otherPlayerLastThreeActions.size() > 3) {
            otherPlayerLastThreeActions.pop();
        }
    }

    public Action getOtherPlayerLastAction() {
        if (otherPlayerLastThreeActions != null || !lastThreeActions.isEmpty()) {
            return otherPlayerLastThreeActions.peekLast();
        }
        else {
            return null;
        }
    }

}
