package game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Match {

    final Logger logger = LoggerFactory.getLogger(Match.class);

    private Player firstPlayer;
    private Player secondPlayer;
    private double numberOfRounds;

    Match(Player firstPlayer, Player secondPlayer, double numberOfRounds) {
        List<Player> players = new ArrayList<>(2);
        players.add(firstPlayer);
        players.add(secondPlayer);
        Collections.shuffle(players);
        this.firstPlayer = players.get(0);
        this.secondPlayer = players.get(1);
        this.numberOfRounds = numberOfRounds;
    }

    private void initiateGame() {
        for (double currentRoundNumber = 1; currentRoundNumber <= numberOfRounds; currentRoundNumber++){

            Action actionOfFirstPlayer = firstPlayer.getNextAction();
            Action actionOfSecondPlayer = secondPlayer.getNextAction();

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
                new TitForTatPlayer(),
                new TitForTatPlayer(),
                10);
        demo.initiateGame();
    }

}
