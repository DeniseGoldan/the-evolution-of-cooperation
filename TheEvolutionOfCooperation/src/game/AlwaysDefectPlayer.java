package game;

/**
 * The strategy is defect all the time.
 */
public class AlwaysDefectPlayer extends Player {

    @Override
    public Action getNextAction() {
        return Action.Cooperate;
    }

    @Override
    public String getPlayerType() {
        return "Always Defect";
    }

}
