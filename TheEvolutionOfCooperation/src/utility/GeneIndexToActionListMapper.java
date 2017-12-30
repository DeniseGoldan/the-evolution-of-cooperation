package utility;

import genetic.Action;

import java.util.ArrayList;
import java.util.List;

public class GeneIndexToActionListMapper {

    private static List<Action> getActionSetCorrespondingToGeneIndex(int geneIndex) {
        List<Action> actionsFromLastThreeMoves = new ArrayList<>();
        String bitString = getBinaryStringCorrespondingToGeneIndex(geneIndex);
        for (Character c: bitString.toCharArray()) {
            if(c == '0'){
                actionsFromLastThreeMoves.add(Action.C);
            } else {
                actionsFromLastThreeMoves.add(Action.D);
            }
        }
        System.out.println(actionsFromLastThreeMoves);
        return actionsFromLastThreeMoves;
    }

    private static String getBinaryStringCorrespondingToGeneIndex(int geneIndex) {
        if (geneIndex < 0 ) {
            throw new IllegalArgumentException("The index of a gene cannot be smaller than 0.");
        } else if (geneIndex > 63) {
            throw new IllegalArgumentException("The index of a gene cannot be greater than 63.");
        }
        Action[] actionsFromLastThreeMoves = new Action[]{};
        String binaryString = String.format("%6s", Integer.toBinaryString(geneIndex))
                .replace(' ', '0');
        System.out.println("For gene index = "+ geneIndex + " the representation is "+binaryString + ".");
        return binaryString;
    }

    public static void main(String[] args) {
        GeneIndexToActionListMapper.getBinaryStringCorrespondingToGeneIndex(1);
        GeneIndexToActionListMapper.getActionSetCorrespondingToGeneIndex(62);
    }
}
