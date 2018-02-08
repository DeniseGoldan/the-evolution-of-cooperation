package game;

import genetic.GeneticAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Match {

    private final Logger logger = LoggerFactory.getLogger(Match.class);
    private Player firstPlayer;
    private Player secondPlayer;

    Match(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    void playMatch() {

        Action actionOfFirstPlayer = firstPlayer.chooseAction(0);
        Action actionOfSecondPlayer = secondPlayer.chooseAction(0);

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

            logger.info("First: " + firstPlayer.getScore() + " [ "+ firstPlayer.getPlayerType()+" ]");
            logger.info("Second: " + secondPlayer.getScore() + " [ "+ secondPlayer.getPlayerType()+" ]\n");

    }

    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        System.out.println(geneticAlgorithm.buildStrategy().getGenes());
        Match demo = new Match(
                geneticAlgorithm.buildStrategy(),
                new TitForTatPlayer()
        );
        demo.playMatch();
    }

}
