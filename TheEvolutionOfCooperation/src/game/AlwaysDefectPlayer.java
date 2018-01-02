package game;

/**
 * The strategy is defect all the time.
 */
public class AlwaysDefectPlayer extends Player {

    @Override
    public Action chooseAction() {
        return Action.Defect;
    }

    @Override
    public String getPlayerType() {
        return "Always Defect";
    }

}
