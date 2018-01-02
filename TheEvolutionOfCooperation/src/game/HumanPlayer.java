package game;

import java.util.Scanner;

public class HumanPlayer extends Player {

    @Override
    public Action getNextAction() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        while (true){
            System.out.println("Type \"Cooperate\" to cooperate and \"Defect\" to defect: ");
            String userInput = reader.next(); // Scans the next token of the input as an int.
            if (userInput.equals("Cooperate")) {
                return Action.Cooperate;
            } else if (userInput.equals("Defect")) {
                return Action.Defect;
            } else {
                System.out.println("Try again... " + userInput +  " is not a valid choice.");
            }
        }
    }

    @Override
    public String getPlayerType() {
        return "HumanPlayer";
    }
}
