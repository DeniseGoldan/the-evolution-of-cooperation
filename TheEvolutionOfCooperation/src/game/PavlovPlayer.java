package game;

/**
 * The strategy is to cooperate as first action. When "Reward" or "Temptation" payoff
 * is received in the last round, then repeat the last action. Otherwise choose the opposite action.
 */
public class PavlovPlayer extends Player {

    @Override
    public Action chooseAction() {
        Action lastAction = getActionFromLastMatch();
        Action lastActionOfOpponent = getOpponentLastMatchAction();
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
