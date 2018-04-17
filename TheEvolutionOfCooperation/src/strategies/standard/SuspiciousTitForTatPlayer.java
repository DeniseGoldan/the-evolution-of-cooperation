package strategies.standard;

import player.Action;
import player.Player;

/**
 * The strategy is to defect on the first move,
 * then copy the opponent’s last move.
 */
public class SuspiciousTitForTatPlayer extends Player {

    @Override
    public Action chooseAction(long iteration) {
        Action actionToPerform = getOpponentActionFromLastRound();
        if (actionToPerform == null) {
            return Action.Defect;
        } else {
            return actionToPerform;
        }
    }

    @Override
    public String getPlayerType() {
        return "Suspicious Tit-For-Tat";
    }

}
