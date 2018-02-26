package strategies.standard;

import strategies.player.Action;
import strategies.player.Player;

import java.util.Random;

import static strategies.player.Action.Cooperate;
import static strategies.player.Action.Defect;

/**
 * The strategy is to perform random actions.
 */
public class RandomPlayer extends Player {

    @Override
    public Action chooseAction(long iteration) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return Cooperate;
        } else {
            return Defect;
        }
    }

    @Override
    public String getPlayerType() {
        return "Random";
    }
}
