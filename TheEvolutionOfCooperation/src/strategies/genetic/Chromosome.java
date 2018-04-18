package strategies.genetic;

import player.Action;
import player.Player;

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

    private final int NUMBER_OF_GENES = 71;
    private List<Action> genes;

    public Chromosome() {
        genes = new ArrayList<>();
        initializeGenesRandomly();
    }

    @Override
    public Action chooseAction(long roundNumber) {
        if (roundNumber == 0) {
            return genes.get(0);
        } else if (roundNumber == 1) {
            Action opponentActionFromLastRound = getOpponentActionFromLastRound();
            if (opponentActionFromLastRound == Action.Cooperate) {
                return genes.get(1);
            } else {
                return genes.get(2);
            }
        } else if (roundNumber == 2) {
            List<Action> opponentActionFromLastTwoRounds = getOpponentActionsFromLastTwoRounds();
            if (opponentActionFromLastTwoRounds.get(0) == Action.Cooperate
                    && opponentActionFromLastTwoRounds.get(1) == Action.Cooperate){
                return genes.get(3);
            } else if (opponentActionFromLastTwoRounds.get(0) == Action.Cooperate
                    && opponentActionFromLastTwoRounds.get(1) == Action.Defect) {
                return genes.get(4);
            } else if (opponentActionFromLastTwoRounds.get(0) == Action.Defect
                    && opponentActionFromLastTwoRounds.get(1) == Action.Cooperate) {
                return genes.get(5);
            } else {
                return genes.get(6);
            }
        } else {
            List<Action> lastThreeRoundsHistory = getBothHistoriesFromLastThreeRounds((int) roundNumber);
            return NextActionHelper.referToHistoryAndChooseAction(this.genes, lastThreeRoundsHistory);
        }
    }

    /**
     * The result is in the order "Your first move", "Opponent's first Move",
     "Your second move", "Opponent's second Move", "Your third move", "Opponent's third Move".
     */
    private List<Action> getBothHistoriesFromLastThreeRounds(int iteration) {
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
        for (int geneIndex = 0; geneIndex < NUMBER_OF_GENES; geneIndex ++) {
            Random random = new Random();
            if (random.nextBoolean()) {
                genes.add(Action.Cooperate);
            } else {
                genes.add(Action.Defect);
            }
        }
    }

    public void printGenes() {
        for (int index = 0; index < NUMBER_OF_GENES; index++) {
            System.out.println(index+"."+genes.get(index));
        }
        System.out.println();
    }

    public List<Action> getGenes() {
        return genes;
    }

    void mutate(double mutationProbability) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double randomNumber = random.nextDouble(0, 1);
        if (randomNumber < mutationProbability) {
            int index = random.nextInt(0, NUMBER_OF_GENES);
            mutateGeneFromIndex(index);
        }
    }

    private void mutateGeneFromIndex(int index) {
        List<Action> result = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_GENES; i++ ) {
            if (i != index) {
                result.add(genes.get(i));
            } else {
                result.add(genes.get(i).getOppositeAction());
            }
        }
        genes = result;
    }

    int getNumberOfGenes() {
        return NUMBER_OF_GENES;
    }

    public void setGenes(List<Action> genes) {
        this.genes = genes;
    }
}
