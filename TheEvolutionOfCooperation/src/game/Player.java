package game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    private static final int INITIAL_SCORE = 0;
    private final Logger logger = LoggerFactory.getLogger(Player.class);
    private long score;
    private final List<Action> actionHistory;
    private final List<Action> opponentActionHistory;

    protected Player() {
        this.score = INITIAL_SCORE;
        this.actionHistory = new ArrayList<>();
        this.opponentActionHistory = new ArrayList<>();
    }

    public abstract Action chooseAction(long iteration);

    public abstract String getPlayerType();

    void updateScore(long valueToBeAdded) {
        this.score += valueToBeAdded;
    }

    public void resetScore() { this.score = INITIAL_SCORE; }

    public long getScore() {
        return this.score;
    }

    public List<Action> getActionHistory() {
        ArrayList<Action> copy = new ArrayList<>();
        if (!actionHistory.isEmpty()){
            copy.addAll(actionHistory);
        }
        return copy;
    }

    public List<Action> getOpponentActionHistory() {
        ArrayList<Action> copy = new ArrayList<>();
        if (!opponentActionHistory.isEmpty()){
            copy.addAll(opponentActionHistory);
        }
        return copy;
    }

    void registerActionToHistory (Action action) {
        actionHistory.add(action);
//        logger.info("Player \"" + getPlayerType() + "\" made the following action: \"" + action +"\"." );
//        logger.info("His history is: " + actionHistory.toString());
    }

    void registerOpponentActionToHistory(Action action) {
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

    public Action getOpponentActionFromLastMatch() {
        if (!opponentActionHistory.isEmpty()) {
            return opponentActionHistory.get(opponentActionHistory.size() - 1);
        }
        else {
            return null;
        }
    }

    public List<Action> getOpponentActionsFromLastTwoMatches() {
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

    boolean hasTheOpponentDefectedLastTwoMatches() {
        List<Action> lastTWoActionsOfOpponent = getOpponentActionsFromLastTwoMatches();
        if(lastTWoActionsOfOpponent != null && !lastTWoActionsOfOpponent.isEmpty()) {
            if(lastTWoActionsOfOpponent.get(0) == lastTWoActionsOfOpponent.get(1)
                    && lastTWoActionsOfOpponent.get(0).equals(Action.Defect)) {
                return true;
            }
        }
        return false;
    }

}
