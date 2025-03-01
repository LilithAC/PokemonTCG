import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class PlayerInput {

    private static Player activePlayer;
    private static Player inactivePlayer;
    private static PkmnGame game;

    private static Scanner in = new Scanner(System.in);
    public static final String ERROR_FRMT = "Invalid choice: %s. Must be a number.";
    public static final String ERROR_NUM = "Invalid choice: %s. Must be a number listed.";
    public static final String ERROR_CUTE = "Not implemented yet :3";

    public static void printMainMenu() {
        System.out.println("----- MAIN MENU -----");
        System.out.println("1 - Play Game");
        System.out.println("2 - Options");
        System.out.println("3 - End Game");
    }

    public static void startGame() {
        System.out.println("Starting game...");

        ArrayList<PkmnCard> deck1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Pikachu pikachu = new Pikachu();
            deck1.add(pikachu);
        }
        for (int i = 0; i < 30; i++) {
            ElectricEnergy electricEnergy = new ElectricEnergy();
            deck1.add(electricEnergy);
        }
        ArrayList<PkmnCard> deck2 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            WaterEnergy waterEnergy = new WaterEnergy();
            deck2.add(waterEnergy);
        }
        for (int i = 0; i < 30; i++) {
            Leafeon leafeon = new Leafeon();
            deck2.add(leafeon);
        }

        Player playerA = new Player(deck1);
        Player playerB = new Player(deck2);

        game = new PkmnGame(playerA, playerB);
        game.startGame();

        Coin coin = new Coin();
        if (coin.flip()) {
            System.out.println("The coin flipped heads. Player 1 goes first.");
            game.gameLoop(playerA, playerB);
        } else {
            System.out.println("The coin flipped tails. Player 2 goes first.");
            game.gameLoop(playerB, playerA);
        }
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


    public static void gameTurn(Player a, Player b) {
        activePlayer = a;
        inactivePlayer = b;

        System.out.println("----- GAME MENU -----");
        System.out.println("1 - Play Card from Hand");
        System.out.print(printHand());
        System.out.println();
        System.out.println("2 - Retreat PKMN");
        System.out.println("3 - Attack PKMN");
        System.out.println("4 - Check PKMN");
        System.out.println("5 - Pass");


        var choice = PlayerInput.getInput();

        while (!List.of(1, 2 ,3, 4, 5).contains(choice)) {
            System.out.println(String.format(ERROR_NUM, choice));

            gameTurn(activePlayer, inactivePlayer);
            choice = PlayerInput.getInput();
        }

        switch (choice) {
            case 1:
                if (activePlayer.getHand().isEmpty()) {
                    System.out.println("You have no cards to play.");
                } else {
                    printHandMenu();
                    break;
                }
            case 2:
                if (activePlayer.getActive() == null) {
                    System.out.println("You have no active Pokemon to retreat.");
                    gameTurn(activePlayer, inactivePlayer);
                } else {
                    printRetreatMenu();
                    break;
                }
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



    //lets player choose a card out of their hand and acts according to card type
    public static void printHandMenu() {
        System.out.println("----- YOUR HAND -----");
        int i = 1;
        for (PkmnCard card : activePlayer.getHand()) {
            System.out.println(i + " - " + card.toString());
            i++;
        }
        System.out.println("----- -----");
        System.out.println(i + " - Back");

        int choice = PlayerInput.getInput();


        try {
            if (choice == i) {
                gameTurn(activePlayer, inactivePlayer);
            }
            String type = activePlayer.getHand().get(choice-1).getClass().getSuperclass().getSimpleName();

            switch (type) {
                case "Pokemon" :
                    //cast this argument as specific subclass bc its coming from an arrayList
                    printPlayPKMNMenu((Pokemon) activePlayer.getHand().get(choice-1));
                    break;
                case "Trainer" :
                    printPlayTrainerMenu((Trainer) activePlayer.getHand().get(choice-1));
                    break;
                case "Energy" :
                    printEnergyMenu((Energy) activePlayer.getHand().get(choice-1));
                    break;
                default:
                    throw new RuntimeException("how did you get here?");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format(ERROR_NUM, choice));
            printHandMenu();
        }

    }

    //formats the names of cards as strings before printing an array of them
    public static ArrayList<String> printHand() {
        ArrayList<String> handF = new ArrayList<>();
        for (PkmnCard card : activePlayer.getHand()) {
            handF.add(card.toString());
        }
        return handF;
    }

    //takes pokemon from hand, plays it to bench, automatically puts pkmn as active if empty
    public static void printPlayPKMNMenu(Pokemon pokemon) {
        if (activePlayer.getActive() == null) {
            System.out.println("First Pokemon must go to active slot.");
            activePlayer.getHand().remove(pokemon);
            activePlayer.setActive(pokemon);
            gameTurn(activePlayer, inactivePlayer);
        } else if (activePlayer.getBench().size() == 5) {
            System.out.println("Your bench is full.");
            gameTurn(activePlayer, inactivePlayer);
        } else {
            System.out.println("Pokemon sent to bench");
            activePlayer.getHand().remove(pokemon);
            activePlayer.getBench().add(pokemon);
            gameTurn(activePlayer, inactivePlayer);
        }
    }

    //TO DO:
    //displays trainer card description and let player decide to play
    public static void printPlayTrainerMenu(Trainer trainer) {

        throw new RuntimeException(ERROR_CUTE);
    }

    //prints both players active pkmn HP and benched pkmn w/ HP
    //please make this print the pkmns energy
    public static void printGameState() {

        System.out.println("----- YOUR PKMN -----");
        //check if there is an active pkmn first
        if (activePlayer.getActive() == null) {
            System.out.println("Active PKMN: None");
        } else {
            System.out.println("Active PKMN: ");
            System.out.println(activePlayer.getActive().toString() + " HP: " + activePlayer.getActive().getHp() + " " + activePlayer.getActive().resToString());
        }

        System.out.println("Benched PKMN: ");
        //check if bench is empty first
        if (activePlayer.getBench().isEmpty()) {
            System.out.println("None");
        } else {
            for (Pokemon pkmn : activePlayer.getBench()) {
                System.out.println(pkmn + " HP: " + pkmn.getHp() + " " + pkmn.resToString());
            }
        }
        System.out.println("----- OPPONENTS PKMN -----");

        if (inactivePlayer.getActive() == null) {
            System.out.println("Active PKMN: None");
        } else {
            System.out.println("Active PKMN: ");
            System.out.println(inactivePlayer.getActive().toString() + " HP: " + inactivePlayer.getActive().getHp());
        }

        System.out.println("Benched PKMN: ");
        if (inactivePlayer.getBench().isEmpty()) {
            System.out.println("None");
        } else {
            for (Pokemon pkmn : inactivePlayer.getBench()) {
                System.out.println(pkmn + " HP: " + pkmn.getHp());
            }
        }

        gameTurn(activePlayer, inactivePlayer);
    }


    public static void printEnergyMenu(Energy energy) {
        if (activePlayer.getEnergyCounter() > 0) {
            System.out.println("You have already played an energy this turn.");
            gameTurn(activePlayer, inactivePlayer);
        } else if (activePlayer.getBench().isEmpty() && activePlayer.getActive() == null) {
            System.out.println("You have no Pokemon to attach energy to.");
            gameTurn(activePlayer, inactivePlayer);
        } else {
            ArrayList<Pokemon> choices = new ArrayList<>();
            choices.addAll(activePlayer.getBench());
            choices.add(activePlayer.getActive());

            System.out.println("Please select a Pokemon to attach energy to: ");
            int i = 1;
            for (Pokemon pkmn : choices) {
                System.out.println(i + " - " + pkmn.toString());
                i++;
            }

            int choice = PlayerInput.getInput();
            //check if choice is OOB
            try {
                activePlayer.attachEnergy(choices.get(choice-1), energy);
                gameTurn(activePlayer, inactivePlayer);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(String.format(ERROR_NUM, choice));
                printEnergyMenu(energy);
            }
        }
    }

    //play picks pokemon card from bench to replace active pokemon
    public static void printRetreatMenu() {
        System.out.println("Would you like to retreat? " + activePlayer.getActive().toString() + "'s retreat cost is " + activePlayer.getActive().retreatCost + " energy.");
        System.out.println("1 - Yes");
        System.out.println("2 - No");

        int choice = PlayerInput.getInput();

        switch (choice) {
            case 1:
                if (activePlayer.getActive().getEnergyRes().size() < activePlayer.getActive().retreatCost) {
                    System.out.println("Not enough energy. " + activePlayer.getActive().toString() + " has " + activePlayer.getActive().getEnergyRes().size() + " energy attached.");
                    gameTurn(activePlayer, inactivePlayer);
                } else {
                    // TO DO:
                    //player needs to choose pokemon to be swapped from bench
                    if (activePlayer.getBench().isEmpty()) {
                        System.out.println("Retreat is not possible. You have no pokemon to replace active.");
                        gameTurn(activePlayer, inactivePlayer);
                    } else {
                        System.out.println("Please choose a pokemon to replace " + activePlayer.getActive());
                        int i = 1;
                        for (Pokemon poke : activePlayer.getBench()) {
                            System.out.println(i + " - " + poke);
                        }

                        int choice2 = PlayerInput.getInput();

                        try {
                            activePlayer.getBench().add(activePlayer.getActive());
                            activePlayer.setActive(activePlayer.getBench().get(choice-1));
                            activePlayer.getBench().remove(choice-1);
                        } catch (IndexOutOfBoundsException e) {


                        }
                    }
                    for (int i = 0; i < activePlayer.getActive().retreatCost; i++) {
                        activePlayer.getActive().getEnergyRes().removeFirst();
                    }
                }
            case 2:
                gameTurn(activePlayer, inactivePlayer);
                break;
            default:
                System.out.println("how did you get here");
        }
    }

    //player chooses a move to attack defending player with, their energy counter is reset and their turn ends
    public static void printAttackMenu() {
        //players cannot attack on each others first turn
        if (game.turnCount == 1 || game.turnCount == 2) {
            System.out.println("You cannot attack during starting turns.");
            gameTurn(activePlayer, inactivePlayer);
        }

        if (activePlayer.getActive() == null) {
            System.out.println("You have no active Pokemon to attack with.");
            gameTurn(activePlayer, inactivePlayer);
        } else {
            System.out.println("Please choose an attack: ");

            int i = 1;
            for (Attack move : activePlayer.getActive().getMoveSet()) {
                System.out.println(i + " - " + move.name + ": " + move.costsToString());
                i++;
            }
            System.out.println(i + " - Back");

            int choice = PlayerInput.getInput();

            //make sure choice is in bounds
            try {
                //check if they chose the back button
                if (choice == i) {
                    gameTurn(activePlayer, inactivePlayer);
                }
                if (activePlayer.getActive().getMoveSet().get(choice-1).costs.isMet(activePlayer.getActive())) {
                    inactivePlayer.getActive().takeDamage(activePlayer.getActive().getMoveSet().get(choice-1).dmg);
                    activePlayer.setEnergyCounter(0);
                    game.turnCount++;
                } else {
                    System.out.println("Not enough energy.");
                    printAttackMenu();
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println(String.format(ERROR_NUM, choice));
                printAttackMenu();
            }
        }
    }

    //asks player if they would like to pass turn without attacking
    public static void printPassMenu() {
        System.out.println("End your turn without attacking?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");

        int choice = PlayerInput.getInput();

        switch (choice) {
            case 1:
                activePlayer.setEnergyCounter(0);
                game.turnCount++;
                break;
            case 2:
                gameTurn(activePlayer, inactivePlayer);
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
