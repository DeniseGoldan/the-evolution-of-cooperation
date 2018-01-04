package genetic;

import game.Action;
import game.Player;
import utility.NextActionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The population is a list of "candidate solutions" (chromosomes that store the solution,
 * also known as strategy, in our case). Each chromosome represents an individual of the population.
 * Chromosomes have a fixed length in this case, 71. The gene is a unit of information, stored inside a chromosome.
 */
public class Chromosome extends Player {

    private static final int LENGTH = 70;
    private List<Action> genes;

    public Chromosome() {
        genes = new ArrayList<>();
        initializeGenesRandomly();
    }

    @Override
    public Action chooseAction(long iteration) {
        if (iteration == 0) {
            return genes.get(0);
        } else if (iteration == 1) {
            Action opponentActionFromLastMatch = getOpponentActionFromLastMatch();
            if (opponentActionFromLastMatch == Action.Cooperate) {
                return genes.get(1);
            } else {
                return genes.get(2);
            }
        } else if (iteration == 2) {
            List<Action> opponentActionFromLastTwoMatches = getOpponentActionsFromLastTwoMatches();
            if (opponentActionFromLastTwoMatches.get(0) == Action.Cooperate
                    && opponentActionFromLastTwoMatches.get(1) == Action.Cooperate){
                return genes.get(3);
            } else if (opponentActionFromLastTwoMatches.get(0) == Action.Cooperate
                    && opponentActionFromLastTwoMatches.get(1) == Action.Defect) {
                return genes.get(4);
            } else if (opponentActionFromLastTwoMatches.get(0) == Action.Defect
                    && opponentActionFromLastTwoMatches.get(1) == Action.Cooperate) {
                return genes.get(5);
            } else {
                return genes.get(6);
            }
        } else {
            // "compute" history of the last three matches
            List<Action> history = getActionHistory().subList((int)iteration-3, (int)iteration);
            List<Action> opponentHistory = getOpponentActionHistory().subList((int)iteration-3, (int)iteration);
            List<Action> lastThreeMatchesHistory = new ArrayList<>();
            for (int index = 0; index < 3; index ++) {
                lastThreeMatchesHistory.add(history.get(index));
                lastThreeMatchesHistory.add(opponentHistory.get(index));
            }
            printGenes();
            return NextActionHelper.getActionCorrespondingToHistory(this.genes, lastThreeMatchesHistory);
        }
    }

    @Override
    public String getPlayerType() {
        return "Chromosome";
    }

    private void initializeGenesRandomly() {
        for (int geneIndex = 0; geneIndex < LENGTH; geneIndex ++) {
            Random random = new Random();
            if (random.nextBoolean()) {
                genes.add(Action.Cooperate);
            } else {
                genes.add(Action.Defect);
            }
        }
    }

    public void printGenes() {
        for (int index = 0; index < LENGTH; index++) {
            System.out.println(index+"."+genes.get(index));
        }
        System.out.println();
    }

    public List<Action> getGenes() {
        return genes;
    }

}
