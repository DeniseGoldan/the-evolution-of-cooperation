package game;

import java.util.List;

/**
 * The strategy is to cooperate on the first move
 * and to defect only when the opponent defects two times in a row.
 */
public class TitForTwoTatsPlayer extends Player {

    private boolean opponentHasDefectedTwoTimesInARow = false;

    @Override
    public Action chooseAction() {

        List<Action> lastTWoActionsOfOpponent = getOpponentActionsFromLastTwoMatches();
        if(lastTWoActionsOfOpponent != null && !lastTWoActionsOfOpponent.isEmpty()) {
            if(lastTWoActionsOfOpponent.get(0) == lastTWoActionsOfOpponent.get(1)
                    && lastTWoActionsOfOpponent.get(0) == Action.Defect) {
                opponentHasDefectedTwoTimesInARow = true;
            }
        }

        if (opponentHasDefectedTwoTimesInARow) {
            return Action.Defect;
        } else {
            Action actionToPerform = getOpponentLastMatchAction();
            if (actionToPerform == null) {
                return Action.Cooperate;
            } else {
                return actionToPerform;
            }
        }
    }

    @Override
    public String getPlayerType() {
        return "Tit-For-Two-Tats";
    }

}
