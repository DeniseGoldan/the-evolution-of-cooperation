package strategies.standard;

import player.Action;
import player.Player;

/**
 * The strategy is to cooperate until the opponent defects,
 * and thereafter to always defect.
 */
public class GrudgerPlayer extends Player {

    private boolean hasTheOpponentDefected = false;

    @Override
    public Action chooseAction(long iteration) {
        if (getOpponentActionFromLastRound() == Action.Defect) {
            hasTheOpponentDefected = true;
        }
        if (hasTheOpponentDefected) {
            return Action.Defect;
        } else {
            return Action.Cooperate;
        }
    }

    @Override
    public String getPlayerType() {
        return "Grudger";
    }

}
