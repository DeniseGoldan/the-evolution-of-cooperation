package strategies.standard;

import strategies.player.Action;
import strategies.player.Player;

/**
 * The strategy is to defect on the first move,
 * then copy the opponent’s last move.
 */
public class SuspiciousTitForTatPlayer extends Player {

    @Override
    public Action chooseAction(long iteration) {
        Action actionToPerform = getOpponentActionFromLastMatch();
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
