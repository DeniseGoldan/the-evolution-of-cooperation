package strategies.genetic;

import player.Action;

import java.util.List;

public class NextActionHelper {

    private final static int PASS_HISTORY_OFFSET = 7;

    /**
     * Add PASS_HISTORY_OFFSET in order to skip the first seven "genes" that represent the decisions for the
     * chromosome to make in the first 3 iterations. The history "CC-CC-CC" can be found at index 7,
     * "CC-CC-CD" at index 8 and so on. The history "DD-DD-DD" can be found at index 70.
     */
    public static Action referToHistoryAndChooseAction(List<Action> genes, List<Action> lastThreeMatches) {
        int actionIndexInGenes = getGeneIndexForThreeLastMatchesHistory(lastThreeMatches);
        return genes.get(actionIndexInGenes);
    }

    static int getGeneIndexForThreeLastMatchesHistory(List<Action> lastThreeMatches) {
        int actionIndexInGenes = 0;
        for (int index = 5; index >= 0; index--) {
            actionIndexInGenes += Math.pow(2, index) * lastThreeMatches.get(5 - index).getBinaryValue();
        }
        actionIndexInGenes += PASS_HISTORY_OFFSET;
        return actionIndexInGenes;
    }

}
