package game;

import genetic.Action;

import java.util.Scanner;

public class HumanPlayer extends Player {

    @Override
    public Action getNextMove() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        while (true){
            System.out.println("Type \"C\" to cooperate and \"D\" to defect: ");
            String userInput = reader.next(); // Scans the next token of the input as an int.
            if (userInput.equals("C")) {
                reader.close();
                return Action.C;
            } else if (userInput.equals("D")) {
                reader.close();
                return Action.D;
            } else {
                System.out.println("Try again... " + userInput +  " is not a valid choice.");
            }
        }
    }

    @Override
    public String getPlayerType() {
        return "Human";
    }
}
