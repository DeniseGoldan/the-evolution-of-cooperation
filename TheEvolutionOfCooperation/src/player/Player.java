package player;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    private long score;
    private List<Action> actionHistory;
    private List<Action> opponentActionHistory;

    protected Player() {
        this.score = 0;
        this.actionHistory = new ArrayList<>();
        this.opponentActionHistory = new ArrayList<>();
    }

    public abstract Action chooseAction(long iteration);

    public abstract String getPlayerType();

    public void updateScore(long valueToBeAdded) {
        this.score += valueToBeAdded;
    }

    public void resetScore() { this.score = 0; }

    public long getScore() {
        return this.score;
    }

    protected List<Action> getActionHistory() {
        ArrayList<Action> copy = new ArrayList<>();
        if (!actionHistory.isEmpty()){
            copy.addAll(actionHistory);
        }
        return copy;
    }

    protected List<Action> getOpponentActionHistory() {
        ArrayList<Action> copy = new ArrayList<>();
        if (!opponentActionHistory.isEmpty()){
            copy.addAll(opponentActionHistory);
        }
        return copy;
    }

    public void registerActionToHistory(Action action) {
        actionHistory.add(action);
    }

    public void registerOpponentActionToHistory(Action action) {
        opponentActionHistory.add(action);
    }

    public Action getActionFromLastMatch() {
        if (!actionHistory.isEmpty()) {
            return actionHistory.get(actionHistory.size() - 1);
        }
        else {
            return null;
        }
    }

    protected Action getOpponentActionFromLastRound() {
        if (!opponentActionHistory.isEmpty()) {
            return opponentActionHistory.get(opponentActionHistory.size() - 1);
        }
        else {
            return null;
        }
    }

    protected List<Action> getOpponentActionsFromLastTwoRounds() {
        if (opponentActionHistory.size() >= 2) {
            int historySize = opponentActionHistory.size();
            List<Action> historySample = new ArrayList<>();
            historySample.add(opponentActionHistory.get(historySize - 2));
            historySample.add(opponentActionHistory.get(historySize - 1));
            return historySample;
        }
        else {
            return new ArrayList<>();
        }
    }

    protected boolean hasTheOpponentDefectedLastTwoMatches() {
        List<Action> lastTWoActionsOfOpponent = getOpponentActionsFromLastTwoRounds();
        if(lastTWoActionsOfOpponent != null && !lastTWoActionsOfOpponent.isEmpty()) {
            if(lastTWoActionsOfOpponent.get(0) == lastTWoActionsOfOpponent.get(1)
                    && lastTWoActionsOfOpponent.get(0).equals(Action.Defect)) {
                return true;
            }
        }
        return false;
    }

    public void resetPersonalAndOpponentHistory() {
        this.actionHistory = new ArrayList<>();
        this.opponentActionHistory = new ArrayList<>();
    }
}
