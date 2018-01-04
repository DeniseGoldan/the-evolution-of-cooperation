package game;

import java.util.Scanner;

public class HumanPlayer extends Player {

    @Override
    public Action chooseAction(long iteration) {
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("Type \"C\" to cooperate and \"D\" to defect: ");
            String userInput = reader.next();
            switch (userInput) {
                case "C":
                    return Action.Cooperate;
                case "D":
                    return Action.Defect;
                default:
                    System.out.println("Try again... " + userInput + " is not a valid choice.");
                    break;
            }
        }
    }

    @Override
    public String getPlayerType() {
        return "HumanPlayer";
    }
}
