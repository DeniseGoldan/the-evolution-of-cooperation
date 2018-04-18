package strategies.standard;

import player.Action;
import player.Player;

/**
 * The strategy is to cooperate on the first move,
 * then copy the opponent’s last move.
 */
public class TitForTatPlayer extends Player {

    @Override
    public Action chooseAction(long iteration) {
        Action actionToPerform = getOpponentActionFromLastRound();
        if (actionToPerform == null) {
            return Action.Cooperate;
        } else {
            return actionToPerform;
        }
    }

    @Override
    public String getPlayerType() { return "Tit-For-Tat"; }

}
