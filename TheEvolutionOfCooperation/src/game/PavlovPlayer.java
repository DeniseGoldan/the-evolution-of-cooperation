package game;

/**
 * The strategy is to cooperate as the first action. When Reward or Temptation payoff
 * is received in the last round then repeat last action. Otherwise choose the opposite action.
 */
public class PavlovPlayer extends Player {

    @Override
    public Action getNextAction() {
        Action lastAction = getLastAction();
        Action lastActionOfOpponent = getLastActionOfOpponent();
        if (lastAction != null) {
            if (lastActionOfOpponent == Action.Cooperate) {
                return lastAction;
            } else {
                return lastAction.getOppositeAction();
            }
        } else {
            return Action.Cooperate;
        }
    }

    @Override
    public String getPlayerType() {
        return "Pavlov";
    }
}
