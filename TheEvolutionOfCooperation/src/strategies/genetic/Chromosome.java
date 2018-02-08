package strategies.genetic;

import strategies.player.Action;
import strategies.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The population is a list of "candidate solutions" (chromosomes that store the solution,
 * also known as strategy, in our case). Each chromosome represents an individual of the population.
 * Chromosomes have a fixed length- in this case, 71.
 * The gene is a unit of information, stored inside a chromosome.
 */
public class Chromosome extends Player {

    private static final int LENGTH = 71;
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
            List<Action> lastThreeMatchesHistory = getBothHistoriesFromLastThreeMatches((int) iteration);
            return NextActionHelper.referToHistoryAndChooseAction(this.genes, lastThreeMatchesHistory);
        }
    }

    /**
     * The result is in the order "Your first move", "Opponent's first Move",
     "Your second move", "Opponent's second Move", "Your third move", "Opponent's third Move".
     */
    private List<Action> getBothHistoriesFromLastThreeMatches(int iteration) {
        List<Action> history = getActionHistory().subList(iteration -3, iteration);
        List<Action> opponentHistory = getOpponentActionHistory().subList(iteration -3, iteration);
        List<Action> lastThreeMatchesHistory = new ArrayList<>();
        for (int index = 0; index < 3; index ++) {
            lastThreeMatchesHistory.add(history.get(index));
            lastThreeMatchesHistory.add(opponentHistory.get(index));
        }
        return lastThreeMatchesHistory;
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

    public void mutate(double mutationProbability) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double randomNumber = random.nextDouble(0, 1);
        if (randomNumber < mutationProbability) {
            int index = random.nextInt(0, LENGTH);
            mutateGeneFromIndex(index);
        }
    }

    private void mutateGeneFromIndex(int index) {
        List<Action> result = new ArrayList<>();
        for (int i = 0; i < LENGTH; i++ ) {
            if (i != index) {
                result.add(genes.get(i));
            } else {
                result.add(genes.get(i).getOppositeAction());
            }
        }
        System.out.println("Mutation index = "+ index +".");
        System.out.println("Before: " + genes);
        System.out.println("After: " + genes);
        genes = result;
    }

}
