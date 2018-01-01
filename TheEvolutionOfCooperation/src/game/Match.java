package game;

import genetic.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Match {

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

    public void initiateGame() {
        for (double currentRoundNumber = 1; currentRoundNumber <= numberOfRounds; currentRoundNumber++){
            Action firstActionToPerform = firstPlayer.getNextAction();
            Action secondActionToPerform = secondPlayer.getNextAction();
            firstPlayer.addOwnActionToDeque(firstActionToPerform);
            secondPlayer.addOwnActionToDeque(secondActionToPerform);
            secondPlayer.addOtherPlayerActionToDeque(firstActionToPerform);
            firstPlayer.addOtherPlayerActionToDeque(secondActionToPerform);

            if (firstActionToPerform == Action.C && secondActionToPerform == Action.C) {
                firstPlayer.updateScore(Payoff.REWARD.getScoreValue());
                secondPlayer.updateScore(Payoff.REWARD.getScoreValue());
            } else if (firstActionToPerform == Action.C && secondActionToPerform == Action.D) {
                firstPlayer.updateScore(Payoff.SUCKER.getScoreValue());
                secondPlayer.updateScore(Payoff.TEMPTATION.getScoreValue());
            } else if (firstActionToPerform == Action.D && secondActionToPerform == Action.C) {
                firstPlayer.updateScore(Payoff.TEMPTATION.getScoreValue());
                secondPlayer.updateScore(Payoff.SUCKER.getScoreValue());
            } else {
                firstPlayer.updateScore(Payoff.PUNISHMENT.getScoreValue());
                secondPlayer.updateScore(Payoff.PUNISHMENT.getScoreValue());
            }

            System.out.println("First: " + firstPlayer.getScore());
            System.out.println("Second: " + secondPlayer.getScore());

        }
    }

    public static void main(String[] args) {
        Match demo = new Match(new HumanPlayer(), new TitForTatPlayer(), 10);
        demo.initiateGame();
    }

}
