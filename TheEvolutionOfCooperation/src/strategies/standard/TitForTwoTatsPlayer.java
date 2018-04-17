package strategies.standard;

import player.Action;
import player.Player;

/**
 * The strategy is to cooperate on the first move
 * and to defect only when the opponent defects two times in a row.
 */
public class TitForTwoTatsPlayer extends Player {

    @Override
    public Action chooseAction(long iteration) {
        if (hasTheOpponentDefectedLastTwoMatches()) {
            return Action.Defect;
        } else {
           return Action.Cooperate;
        }
    }

    @Override
    public String getPlayerType() {
        return "Tit-For-Two-Tats";
    }

}
