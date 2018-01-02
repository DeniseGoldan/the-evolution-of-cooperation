package game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    private double score;
    private List<Action> actionHistory;
    private List<Action> opponentActionHistory;

    Player() {
        this.score = 0;
        this.actionHistory = new ArrayList<>();
        this.opponentActionHistory = new ArrayList<>();
    }

    public abstract Action getNextAction();

    public abstract String getPlayerType();

    void updateScore(double valueToBeAdded) {
        this.score += valueToBeAdded;
    }

    double getScore() {
        return this.score;
    }

    void registerActionToHistory (Action action) {
        actionHistory.add(action);
        System.out.println("Player "+ getPlayerType() + " made the following move: \"" + action +"\"" );
        System.out.println(actionHistory.toString());
    }

    public Action getLastAction() {
        if (actionHistory != null && !actionHistory.isEmpty()) {
            return actionHistory.get(actionHistory.size() - 1);
        }
        else {
            return null;
        }
    }

    void registerOpponentActionToHistory(Action action) {
        opponentActionHistory.add(action);
    }

    Action getLastActionOfOpponent() {
        if (opponentActionHistory != null && !opponentActionHistory.isEmpty()) {
            return opponentActionHistory.get(opponentActionHistory.size() - 1);
        }
        else {
            return null;
        }
    }

    List<Action> getLastTwoActionsOfOpponent() {
        if (opponentActionHistory != null && opponentActionHistory.size() >= 2) {
            int historySize = opponentActionHistory.size();
            List<Action> historySample = new ArrayList<>();
            historySample.add(opponentActionHistory.get(historySize - 2));
            historySample.add(opponentActionHistory.get(historySize - 1));
            return historySample;
        }
        else {
            return null;
        }
    }


}
