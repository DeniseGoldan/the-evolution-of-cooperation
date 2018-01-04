package utility;

import game.Action;
import genetic.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class NextActionHelper {

    public static Action getActionCorrespondingToHistory(List<Action> genes, List<Action> actualComputedHistory) {
        int actionIndexInGenes = 0;
        for (int index = 0; index <= 5; index++) {
            actionIndexInGenes += Math.pow(2, index) * actualComputedHistory.get(index).getBinaryValue();
        }
        System.out.println("action index = "+ actionIndexInGenes);
        // add 7 to index, in order to ignore history part of the chromosome
        // the element on position with index 7 is CC-CC-CC
        return genes.get(actionIndexInGenes + 7);
    }

    public static void main(String[] args) throws Exception {
        Chromosome chromosome = new Chromosome();
        chromosome.printGenes();
        List<Action> actualComputedHistory = new ArrayList<>();

        actualComputedHistory.add(Action.Cooperate);
        actualComputedHistory.add(Action.Cooperate);

        actualComputedHistory.add(Action.Cooperate);
        actualComputedHistory.add(Action.Cooperate);

        actualComputedHistory.add(Action.Cooperate);
        actualComputedHistory.add(Action.Cooperate);

        System.out.println(
                NextActionHelper
                        .getActionCorrespondingToHistory(chromosome.getGenes(), actualComputedHistory));
    }
}
