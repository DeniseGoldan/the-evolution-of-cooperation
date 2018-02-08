package strategies.standard;

import strategies.player.Action;
import strategies.player.Player;

import java.util.Random;

/**
 * The strategy is to perform random actions.
 */
public class RandomPlayer extends Player {

    @Override
    public Action chooseAction(long iteration) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return Action.Cooperate;
        } else {
            return Action.Defect;
        }
    }

    @Override
    public String getPlayerType() {
        return "Random";
    }
}
