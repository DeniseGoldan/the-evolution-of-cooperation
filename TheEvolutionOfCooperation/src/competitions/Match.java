package competitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import strategies.player.Action;
import strategies.player.Payoff;
import strategies.player.Player;

/**
 * A match MUST have a number of rounds greater than 1, because the chromosome of the genetic
 * algorithm adapts using the number of rounds hyper-parameter. The chromosome must check the history.
 * If the number of rounds would be 1, the action chosen by the genetic algorithm would be
 * the same for every match.
 */
public class Match {

    private final Logger logger = LoggerFactory.getLogger(Match.class);
    private Player firstPlayer;
    private Player secondPlayer;
    private long numberOfRounds;

    Match(Player firstPlayer, Player secondPlayer, long numberOfRounds) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.numberOfRounds = numberOfRounds;
    }

    void playMatch() {

        for (long roundNumber = 0; roundNumber < this.numberOfRounds; roundNumber++) {

            Action actionOfFirstPlayer = firstPlayer.chooseAction(roundNumber);
            Action actionOfSecondPlayer = secondPlayer.chooseAction(roundNumber);

            firstPlayer.registerActionToHistory(actionOfFirstPlayer);
            secondPlayer.registerActionToHistory(actionOfSecondPlayer);

            firstPlayer.registerOpponentActionToHistory(actionOfSecondPlayer);
            secondPlayer.registerOpponentActionToHistory(actionOfFirstPlayer);

            if (actionOfFirstPlayer == Action.Cooperate && actionOfSecondPlayer == Action.Cooperate) {
                firstPlayer.updateScore(Payoff.Reward.getScoreValue());
                secondPlayer.updateScore(Payoff.Reward.getScoreValue());
            } else if (actionOfFirstPlayer == Action.Cooperate && actionOfSecondPlayer == Action.Defect) {
                firstPlayer.updateScore(Payoff.SuckerPayoff.getScoreValue());
                secondPlayer.updateScore(Payoff.Temptation.getScoreValue());
            } else if (actionOfFirstPlayer == Action.Defect && actionOfSecondPlayer == Action.Cooperate) {
                firstPlayer.updateScore(Payoff.Temptation.getScoreValue());
                secondPlayer.updateScore(Payoff.SuckerPayoff.getScoreValue());
            } else {
                firstPlayer.updateScore(Payoff.Punishment.getScoreValue());
                secondPlayer.updateScore(Payoff.Punishment.getScoreValue());
            }

            logger.info("First: " + firstPlayer.getScore() + " [ " + firstPlayer.getPlayerType() + " ]");
            logger.info("Second: " + secondPlayer.getScore() + " [ " + secondPlayer.getPlayerType() + " ]\n");

        }
    }

}
