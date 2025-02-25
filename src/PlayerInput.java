import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class PlayerInput {

    private static Player player;
    private static PkmnGame game;
    private static Scanner in = new Scanner(System.in);
    private static final String ERROR_FRMT = "Invalid choice: %s. Must be a number.";
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
            deck1.add(pikachu);
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

        while (!List.of(1, 2 ,3, 4, 5).contains(choice)) {
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
                throw new RuntimeException("how did you get here?");
        }
    }



    public static void printHandMenu() {
        System.out.println("----- YOUR HAND -----");
        printHand();

        int choice = PlayerInput.getInput();
        String type = player.getHand().get(choice).getClass().getSuperclass().getSimpleName();

        switch (type) {
            case "Pokemon" :
                printPlayPKMNMenu((Pokemon) player.getHand().get(choice));
                break;
            case "Trainer" :
                printPlayTrainerMenu((Trainer) player.getHand().get(choice));
                break;
            case "Energy" :
                printEnergyMenu((Energy) player.getHand().get(choice));
                break;
            default:
                throw new RuntimeException("how did you get here?");
        }
    }


    //takes pokemon from hand, plays it to bench, automatically puts pkmn as active if empty
    public static void printPlayPKMNMenu(Pokemon pokemon) {
        if (player.getActive() == null) {
            System.out.println("First Pokemon must go to active slot.");
            player.getHand().remove(pokemon); //i hope this doesnt do anything unexpected
            player.setActive(pokemon);
            printGameMenu();
        } else {
            System.out.println("Pokemon sent to bench");
            player.getHand().remove(pokemon);
            player.getBench().add(pokemon);
            printGameMenu();
        }
    }

    //TO DO:
    //displays trainer card description and let player decide to play
    public static void printPlayTrainerMenu(Trainer trainer) {
        throw new RuntimeException(ERROR_CUTE);
    }
    
    public static void printHand() {
        int i = 1;
        for (PkmnCard card : player.getHand()) {
            System.out.println(i + " - " + card.toString());
            i++;
        }
    }

    //needs to print both players active pkmn HP and benched pkmn w/ HP
    public static void printGameState() {

        System.out.println("----- YOUR PKMN -----");
        //check if theres an active pkmn first
        if (player.getActive() == null) {
            System.out.println("Active PKMN: None");
        } else {
            System.out.println("Active PKMN: ");
            System.out.println(player.getActive().toString() + " HP: " + player.getActive().getHp());
        }

        System.out.println("Benched PKMN: ");
        //check if bench is empty first
        if (player.getBench().isEmpty()) {
            System.out.println("None");
        } else {
            for (Pokemon pkmn : player.getBench()) {
                System.out.println(pkmn + " HP: " + pkmn.getHp());
            }
        }
        System.out.println("----- OPPONENTS PKMN -----");
        System.out.println("we didnt get that far");

        printGameMenu();
    }


    public static void printEnergyMenu(Energy energy) {
        if (player.getEnergyCounter() > 0) {
            System.out.println("You have already played an energy this turn.");
            printGameMenu();
        } else if (player.getBench().isEmpty() && player.getActive() == null) {
            System.out.println("You have no Pokemon to attach energy to.");
            printGameMenu();
        } else {
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
            //check if choice is OOB
            try {
                choices.get(choice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Not valid option. Must be a number listed on screen");
            }

            player.attachEnergy(choices.get(choice), energy);
            printGameMenu();
        }
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
        System.out.println("Please choose an attack");
        int i = 1;
        for (String move : player.getActive().getMoveSet()) {
            System.out.println(i + " - " + move);
            i++;
        }

        int choice = PlayerInput.getInput();
        try {
            player.getActive().getMoveSet().get(choice);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format(ERROR_NUM, choice));
        }
    }

    //TO DO:
    //should ask player if they are sure they would not like to attack
    //end players turn AND reset energy counter to 0 !!!
    public static void printPassMenu() {
        System.out.println("End your turn without attacking?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");

        int choice = PlayerInput.getInput();

        switch (choice) {
            case 1:
                throw new RuntimeException(ERROR_CUTE);
            case 2:
                printGameMenu();
                break;
            default:
                System.out.println("how did you get here");
        }
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
                System.out.println(String.format(ERROR_FRMT, next));
            }

            printMainMenu();
            next = PlayerInput.in.next();
        }

        return choice;
    }
}
