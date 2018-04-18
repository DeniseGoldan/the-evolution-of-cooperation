package competitions;

import player.Action;
import player.Payoff;
import player.Player;

/**
 * A match MUST have a number of rounds greater than 1, because the chromosome of the genetic
 * algorithm adapts using the number of rounds hyper-parameter. The chromosome must check the history.
 * If the number of rounds would be 1, the action chosen by the genetic algorithm would be
 * the same for every match.
 */
class Match {

    private Player firstPlayer;
    private Player secondPlayer;
    private long numberOfRounds;

    Match(Player firstPlayer, Player secondPlayer, long numberOfRounds) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.numberOfRounds = numberOfRounds;
    }

    /**
     * A match consists of two players that secretly chose a move to make in each round.
     * At the end of each round, their score must be updated with a value that matches
     * the move they made, based on the move the other player has made.
     */
    void playMatch() {

        for (long roundNumber = 0; roundNumber < this.numberOfRounds; roundNumber++) {

            Action actionOfFirstPlayer = firstPlayer.chooseAction(roundNumber);
            Action actionOfSecondPlayer = secondPlayer.chooseAction(roundNumber);

            firstPlayer.registerActionToHistory(actionOfFirstPlayer);
            secondPlayer.registerActionToHistory(actionOfSecondPlayer);

            firstPlayer.registerOpponentActionToHistory(actionOfSecondPlayer);
            secondPlayer.registerOpponentActionToHistory(actionOfFirstPlayer);

            updateBothPlayersScore(actionOfFirstPlayer, actionOfSecondPlayer);

        }
    }

    /**
     * Based on the moves chosen in the current round, the players will be rewarded with a score.
     * There are 4 possible combinations for the moves chosen by the players and each combination grants them
     * a corresponding payoff.
     */
    private void updateBothPlayersScore(Action actionOfFirstPlayer, Action actionOfSecondPlayer) {
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
    }

}
