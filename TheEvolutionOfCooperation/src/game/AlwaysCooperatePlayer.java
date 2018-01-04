package game;

/**
 * The strategy is cooperate all the time.
 */
public class AlwaysCooperatePlayer extends Player {

    @Override
    public Action chooseAction(long iteration) {
        return Action.Cooperate;
    }

    @Override
    public String getPlayerType() {
        return "Always Cooperate";
    }

}
