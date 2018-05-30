package strategies.standard;

import player.Action;
import player.Player;

import java.util.Objects;

/**
 * The strategy is to cooperate until the opponent defects,
 * and thereafter to always defect.
 */
public class GrudgerPlayer extends Player {

    private boolean hasTheOpponentDefected = false;

    @Override
    public Action chooseAction(long iteration) {
        /*
        In a tournament with elimination, the grudger will play multiple matches with
        * different players. He MUST reset hasTheOpponentDefected to FALSE before he makes
        * his first choice in a new match. (Otherwise, if he defected in a previous round,
        * he will continue to do so until the reference is erased.)
        * */
        if (iteration == 0) {
            hasTheOpponentDefected = false;
            return Action.Cooperate;
        }

        if (Objects.equals(getOpponentActionFromLastRound(), Action.Defect)) {
            hasTheOpponentDefected = true;
        }
        if (hasTheOpponentDefected) {
            return Action.Defect;
        } else {
            return Action.Cooperate;
        }
    }

    @Override
    public String getPlayerType() { return "Grudger"; }

}
