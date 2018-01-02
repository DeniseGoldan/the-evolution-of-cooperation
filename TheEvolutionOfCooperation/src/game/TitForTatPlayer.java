package game;

/**
 * The strategy is to cooperate on the first move,
 * then copy the opponent’s last move.
 */
public class TitForTatPlayer extends Player {

    @Override
    public Action chooseAction() {
        Action actionToPerform = getOpponentLastMatchAction();
        if (actionToPerform == null) {
            return Action.Cooperate;
        } else {
            return actionToPerform;
        }
    }

    @Override
    public String getPlayerType() {
        return "Tit-For-Tat";
    }

}
