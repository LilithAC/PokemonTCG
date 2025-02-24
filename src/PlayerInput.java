import java.util.InputMismatchException;
import java.util.Scanner;

public class PlayerInput {

    private Player player;
    private static Scanner in;

    public void main(String[] args) {

        printMainMenu();
        var choice = in.nextInt();

        switch (choice) {
            case 1 : startGame();
                break;
            case 2 : options();
                break;
            case 3 : endGame();
                break;
            default:
                throw new IllegalArgumentException("how");
        }


    }

    public void printMainMenu() {
        System.out.println("----- MAIN MENU -----");
        System.out.println("1 - Play Game");
        System.out.println("2 - Options");
        System.out.println("3 - End Game");
    }

    //for each card in the players hand,
    //a number should be printed that corresponds to that card
    public void printHand() {
        for (PkmnCard card : player.getHand()) {
            System.out.println(card);
        }
    }

    //needs to print both players active pkmn HP and benched pkmn
    public void printGameState() {
        System.out.println("----- YOUR PKMN -----");
        System.out.println("Active PKMN: " + player.getActive().toString() + "HP: " + player.getActive().getHp());
        System.out.println("Benched PKMN: ");
        for (Pokemon pkmn : player.getBench()) {
            System.out.println(pkmn + "HP: " + pkmn.getHp());
        }
        System.out.println("----- OPPONENTS PKMN");

    }

    public void startGame() {
        System.out.println("Starting game...");
        printGameMenu();
    }

    public void options() {
        System.out.println("----- OPTIONS -----");

    }

    public void endGame() {
        System.out.println("Bye.");
    }

    public void printGameMenu() {
        System.out.println("----- GAME MENU -----");
        System.out.println("1 - Play Card from Hand");
        System.out.println("2 - Retreat PKMN");
        System.out.println("3 - Attack PKMN");
        System.out.println("4 - Pass");
    }

    //TO DO:
    //should print out player's entire hand with number options from each
    //when player chooses a number, program should act according to card type
    //Trainer cards should be used and discarded
    //Monster cards should be benched
    //Energy cards should be attempted to be attached --> leads to energy subMenu
    public void printHandMenu() {
        System.out.println("----- YOUR HAND -----");
        printHand();
    }

    //TO DO:
    //first check if player has already played an energy
    //player should select a pkmn from array of benched + active pkmn
    //player should select an energy to attach
    public void printEnergyMenu() {
        if (player.getEnergyCounter() > 0) {
            System.out.println("You have already played an energy this turn.");
        }
        System.out.println("Please select a Pokemon to attach energy to: ");
        for (int i = 0; i < player.getBench().size(); i++) {
            System.out.print(i + player.getBench().get(i).toString());
        }
    }

    //TO DO:
    //should take active pkmn and send it to bench using energy attached to pkmn
    //then player chooses new active pokemon from bench
    public void printRetreatMenu() {

    }

    //TO DO:
    //needs to have player choose PKMN to attack, then choose a move to use
    //then end player's turn AND reset energy counter to 0 !!!
    //if there is no active pokemon throw error
    public void printAttackMenu() {
        System.out.println("Please choose an attack");
        for (String move : player.getActive().getMoveSet()) {
            int i = 1;
            System.out.println(i + move);
            i++;
        }

    }

    //TO DO:
    //should ask player if they are sure they would not like to attack
    //end players turn AND reset energy counter to 0 !!!
    public void passMenu() {
        System.out.println();
    }

    //only checks if player input was an integer, NOT if it was a valid choice
    public int getInput() {
        int choice;

        String next = PlayerInput.in.next();
        while (true) {
            try {
                choice = Integer.parseInt(next);
                break;
            } catch (NumberFormatException e) {
                System.out.println("noe");
            }

            printMainMenu();
            next = PlayerInput.in.next();
        }

        return choice;
    }
}
