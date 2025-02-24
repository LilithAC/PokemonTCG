import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class PlayerInput {

    private static Player player;
    private static PkmnGame game;
    private static Scanner in = new Scanner(System.in);
    private static final String ERROR = "Invalid choice: %s. Must be a number.";
    private static final String ERROR_NUM = "Invalid choice: %s. Must be a number listed.";
    private static final String ERROR_CUTE = "Not implemented yet :3";

    public static void main(String[] args) {
        printMainMenu();
        var choice = PlayerInput.getInput();

        //makes sure player's choice is one of the options listed
        while (!List.of(1, 2 ,3).contains(choice)) {
            System.out.println(String.format(ERROR_NUM, choice));

            printMainMenu();
            choice = PlayerInput.getInput();
        }

        switch (choice) {
            case 1 : startGame();
                break;
            case 2 : options();
                break;
            case 3 : endGame();
                break;
            default:
                throw new IllegalArgumentException(String.format(ERROR_NUM, choice));
        }
    }

    public static void printMainMenu() {
        System.out.println("----- MAIN MENU -----");
        System.out.println("1 - Play Game");
        System.out.println("2 - Options");
        System.out.println("3 - End Game");
    }

    //TO DO:
    //needs to initialize 2 players with decks and start a game with them
    //coin flip should decide turn order
    public static void startGame() {
        System.out.println("Starting game...");

        WaterEnergy waterEnergy = new WaterEnergy();
        Pikachu pikachu = new Pikachu();
        ArrayList<PkmnCard> deck1 = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            deck1.add(waterEnergy);
        }
        ArrayList<PkmnCard> deck2 = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            deck2.add(waterEnergy);
        }

        Player player1 = new Player(deck1);
        Player player2 = new Player(deck2);

        game = new PkmnGame(player1, player2);
        player = player1;
        game.startGame();
        printGameMenu();
    }

    //TO DO:
    //make opponent configurable to be AI or human ran
    public static void options() {
        System.out.println("----- OPTIONS -----");

    }

    public static void endGame() {
        System.out.println("Bye.");
        game.stopGame();
    }

    public static void printGameMenu() {
        System.out.println("----- GAME MENU -----");
        System.out.println("1 - Play Card from Hand");
        System.out.println("2 - Retreat PKMN");
        System.out.println("3 - Attack PKMN");
        System.out.println("4 - Check PKMN");
        System.out.println("5 - Pass");


        var choice = PlayerInput.getInput();

        while (!List.of(1, 2 ,3, 4).contains(choice)) {
            System.out.println(String.format(ERROR_NUM, choice));

            printGameMenu();
            choice = PlayerInput.getInput();
        }

        switch (choice) {
            case 1:
                printHandMenu();
                break;
            case 2:
                printRetreatMenu();
                break;
            case 3:
                printAttackMenu();
                break;
            case 4:
                printGameState();
                break;
            case 5:
                printPassMenu();
                break;
            default:
                throw new IllegalArgumentException(String.format(ERROR_NUM, choice));
        }
    }


    //TO DO:
    //should print out player's entire hand with number options from each
    //when player chooses a number, program should act according to card type
    //Trainer cards should be used and discarded
    //Monster cards should be benched
    //Energy cards should be attempted to be attached --> leads to energy subMenu
    public static void printHandMenu() {
        System.out.println("----- YOUR HAND -----");
        printHand();

        int choice = PlayerInput.getInput();
        String type = player.getHand().get(choice).getClass().getSuperclass().getSimpleName();

        switch (type) {
            case "Pokemon" :
                printPlayPKMNMenu();
                break;
            case "Trainer" :
                printPlayTrainerMenu();
                break;
            case "Energy" :
                printEnergyMenu((Energy) player.getHand().get(choice));
                break;
            default:
                throw new RuntimeException("how did you get here?");
        }
    }

    public static void printPlayPKMNMenu() {
        throw new RuntimeException(ERROR_CUTE);
    }

    //TO DO:
    //displays trainer card description and let player decide to play
    public static void printPlayTrainerMenu() {
        throw new RuntimeException(ERROR_CUTE);
    }

    //for each card in the players hand,
    //a number should be printed that corresponds to that card
    public static void printHand() {
        int i = 1;
        for (PkmnCard card : player.getHand()) {
            System.out.println(i + " - " + card.toString());
            i++;
        }
    }

    //needs to print both players active pkmn HP and benched pkmn
    public static void printGameState() {
        System.out.println("----- YOUR PKMN -----");
        System.out.println("Active PKMN: " + player.getActive().toString() + "HP: " + player.getActive().getHp());
        System.out.println("Benched PKMN: ");
        for (Pokemon pkmn : player.getBench()) {
            System.out.println(pkmn + "HP: " + pkmn.getHp());
        }
        System.out.println("----- OPPONENTS PKMN");

    }

    //TO DO:
    //first check if player has already played an energy
    //player should select a pkmn from array of benched + active pkmn
    public static void printEnergyMenu(Energy energy) {
        if (player.getEnergyCounter() > 0) {
            System.out.println("You have already played an energy this turn.");
        }

        ArrayList<Pokemon> choices = new ArrayList<>();
        choices.addAll(player.getBench());
        choices.add(player.getActive());

        System.out.println("Please select a Pokemon to attach energy to: ");
        int i = 1;
        for (Pokemon pkmn : choices) {
            System.out.print(i + " - " + pkmn.toString());
            i++;
        }

        int choice = PlayerInput.getInput();

        //needs to check that choice was in array bounds
        try {
            choices.get(choice);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not valid option. Must be number listed on screen");
        }

        player.attachEnergy(choices.get(choice), energy);
    }

    //TO DO:
    //should take active pkmn and send it to bench using energy attached to pkmn
    //then player chooses new active pokemon from bench
    public static void printRetreatMenu() {
        throw new RuntimeException(ERROR_CUTE);
    }

    //TO DO:
    //needs to have player choose PKMN to attack, then choose a move to use
    //then end player's turn AND reset energy counter to 0 !!!
    //if there is no active pokemon throw error
    public static void printAttackMenu() {
        throw new RuntimeException(ERROR_CUTE);
        /**
        System.out.println("Please choose an attack");
        for (String move : player.getActive().getMoveSet()) {
            int i = 1;
            System.out.println(i + move);
            i++;
        }
         **/

    }

    //TO DO:
    //should ask player if they are sure they would not like to attack
    //end players turn AND reset energy counter to 0 !!!
    public static void printPassMenu() {
        throw new RuntimeException("Not implemented yet :3");
    }

    //only checks if player input was an integer, NOT if it was a valid choice
    public static int getInput() {
        int choice;
        String next = PlayerInput.in.next();

        while (true) {
            try {
                choice = Integer.parseInt(next);
                break;
            } catch (NumberFormatException e) {
                System.out.println(String.format(ERROR, next));
            }

            printMainMenu();
            next = PlayerInput.in.next();
        }

        return choice;
    }
}
