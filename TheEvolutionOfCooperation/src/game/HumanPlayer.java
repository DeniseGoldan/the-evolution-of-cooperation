package game;

import java.util.Scanner;

public class HumanPlayer extends Player {

    @Override
    public Action chooseAction() {
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("Type \"C\" to cooperate and \"D\" to defect: ");
            String userInput = reader.next();
            switch (userInput) {
                case "Cooperate":
                    return Action.Cooperate;
                case "Defect":
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
