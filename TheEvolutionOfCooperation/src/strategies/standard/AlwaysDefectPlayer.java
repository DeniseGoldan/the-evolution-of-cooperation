package strategies.standard;

import player.Action;
import player.Player;

/**
 * The strategy is defect all the time.
 */
public class AlwaysDefectPlayer extends Player {

    @Override
    public Action chooseAction(long iteration) { return Action.Defect; }

    @Override
    public String getPlayerType() { return "Always Defect"; }

}
