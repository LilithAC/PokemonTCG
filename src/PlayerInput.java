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

        Player playerA = new Player(deck1);
        Player playerB = new Player(deck2);

        game = new PkmnGame(playerA, playerB);
        activePlayer = playerA;
        inactivePlayer = playerB;
        game.startGame();

        Coin coin = new Coin();
        if (coin.flip()) {
            System.out.println("The coin flipped heads. Player 1 goes first.");
            gameTurn(activePlayer, inactivePlayer);
        } else {
            System.out.println("The coin flipped tails. Player 2 goes first.");
            gameTurn(inactivePlayer, activePlayer);
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
        System.out.print("Your hand: [ ");
        for (PkmnCard card : activePlayer.getHand()) {
            System.out.print(card.toString() + ", ");
        }
        System.out.print("]");
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



    public static void printHandMenu() {
        System.out.println("----- YOUR HAND -----");
        printHand();

        int choice = PlayerInput.getInput();
        String type = activePlayer.getHand().get(choice).getClass().getSuperclass().getSimpleName();

        switch (type) {
            case "Pokemon" :
                printPlayPKMNMenu((Pokemon) activePlayer.getHand().get(choice));
                break;
            case "Trainer" :
                printPlayTrainerMenu((Trainer) activePlayer.getHand().get(choice));
                break;
            case "Energy" :
                printEnergyMenu((Energy) activePlayer.getHand().get(choice));
                break;
            default:
                throw new RuntimeException("how did you get here?");
        }
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
    
    public static void printHand() {
        int i = 1;
        for (PkmnCard card : activePlayer.getHand()) {
            System.out.println(i + " - " + card.toString());
            i++;
        }
    }

    //prints both players active pkmn HP and benched pkmn w/ HP
    public static void printGameState() {

        System.out.println("----- YOUR PKMN -----");
        //check if there is an active pkmn first
        if (activePlayer.getActive() == null) {
            System.out.println("Active PKMN: None");
        } else {
            System.out.println("Active PKMN: ");
            System.out.println(activePlayer.getActive().toString() + " HP: " + activePlayer.getActive().getHp());
        }

        System.out.println("Benched PKMN: ");
        //check if bench is empty first
        if (activePlayer.getBench().isEmpty()) {
            System.out.println("None");
        } else {
            for (Pokemon pkmn : activePlayer.getBench()) {
                System.out.println(pkmn + " HP: " + pkmn.getHp());
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

            activePlayer.attachEnergy(choices.get(choice), energy);
            gameTurn(activePlayer, inactivePlayer);
        }
    }

    //TO DO:
    //should take active pkmn and send it to bench using energy attached to pkmn
    //then player chooses new active pokemon from bench
    public static void printRetreatMenu() {
        System.out.println("Would you like to retreat?" + activePlayer.getActive().toString() + "'s retreat cost is " + activePlayer.getActive().retreatCost);
        System.out.println("1 - Yes");
        System.out.println("2 - No");

        int choice = PlayerInput.getInput();

        switch (choice) {
            case 1:
                if (activePlayer.getActive().getEnergyRes().size() < activePlayer.getActive().retreatCost) {
                    System.out.println("Not enough energy. " + activePlayer.getActive().toString() + "'s has " + activePlayer.getActive().getEnergyRes().size() + " energy attached.");
                    gameTurn(activePlayer, inactivePlayer);
                } else {
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
        if (activePlayer.getActive() == null) {
            System.out.println("You have no active Pokemon to attack with.");
            gameTurn(activePlayer, inactivePlayer);
        } else {
            System.out.println("Please choose an attack: ");

            int i = 1;
            for (Attack move : activePlayer.getActive().getMoveSet()) {
                System.out.println(i + " - " + move.name);
                i++;
            }

            int choice = PlayerInput.getInput();

            //make sure choice is in bounds
            try {
                if (activePlayer.getActive().getMoveSet().get(choice).costs.isMet(activePlayer.getActive())) {
                    inactivePlayer.getActive().takeDamage(activePlayer.getActive().getMoveSet().get(choice+1).dmg);
                    activePlayer.setEnergyCounter(0);
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
                activePlayer.setEnergyCounter(0);
                throw new RuntimeException(ERROR_CUTE);
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
