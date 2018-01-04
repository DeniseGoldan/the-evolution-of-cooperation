package game;

import genetic.Chromosome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Match {

    private final Logger logger = LoggerFactory.getLogger(Match.class);
    private Player firstPlayer;
    private Player secondPlayer;
    private double numberOfRounds;

    Match(Player firstPlayer, Player secondPlayer, double numberOfRounds) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.numberOfRounds = numberOfRounds;
    }

    private void initiateGame() {
        for (long currentRoundNumber = 0; currentRoundNumber < numberOfRounds; currentRoundNumber++){

            System.out.println("Current round index: " + currentRoundNumber);

            Action actionOfFirstPlayer = firstPlayer.chooseAction(currentRoundNumber);
            Action actionOfSecondPlayer = secondPlayer.chooseAction(currentRoundNumber);

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

            logger.info("First: " + firstPlayer.getScore());
            logger.info("Second: " + secondPlayer.getScore());

        }
    }

    public static void main(String[] args) {
        Match demo = new Match(
                new Chromosome(),
                new HumanPlayer(),
                10);
        demo.initiateGame();
    }

}
