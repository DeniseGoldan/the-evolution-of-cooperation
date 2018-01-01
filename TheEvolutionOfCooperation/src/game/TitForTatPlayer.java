package game;

import genetic.Action;

import java.util.Random;

public class TitForTatPlayer extends Player {

    private Action otherPlayerLastAction;

    @Override
    public Action getNextAction() {
        Action actionToPerform = getOtherPlayerLastAction();
        if (actionToPerform == null) {
            // This player must make the first move, but his moves are a replica
            // of the other player's last move. When he is first, the Tit-For-Tat
            // player will perform a random move.
            Random random = new Random();
            return getRandomAction(random);
        } else {
            return actionToPerform;
        }
    }

    private Action getRandomAction(Random random) {
        if (random.nextBoolean()) {
            return Action.C;
        } else {
            return Action.D;
        }
    }

    @Override
    public String getPlayerType() {
        return "Tit-For-Tat";
    }
}
