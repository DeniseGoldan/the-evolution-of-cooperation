package strategies.standard;

import player.Action;
import player.Player;

import java.util.Random;

import static player.Action.Cooperate;
import static player.Action.Defect;

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
