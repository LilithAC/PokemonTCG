import java.util.ArrayList;
import java.util.List;

public class GameStateHandle {

    protected static Player activePlayer;
    protected static Player inactivePlayer;
    private static PkmnGame game;
    private static State state = State.MAIN;

    public static final String ERROR_NUM = "Invalid choice: %s. Must be a number listed.";
    public static final String ERROR_CUTE = "Not implemented yet :3";

    /**TO DO:
     * add back buttons to game menus
     * create java doc
    **/

    //assumes choice is parsed int
    public static void step(int choice) {

        switch(state) {
            case MAIN:
                handleMain(choice);
                break;
            case OPTION:
                printOptions();
                handleOptions(choice);
                break;
            case EXIT:
                handleExit(choice);
                break;
            case TURN:
                handleTurn(choice);
                break;
            case HAND:
                handleHand(choice);
                break;
            case ATTACK:
                handleAttack(choice);
                break;
            case RETREAT:
                handleRetreat(choice);
                break;
            case PASS:
                handlePass(choice);
                break;
            case KO:
                handleKO(choice);
                break;
            case WINNER:
                handleWinner();
                break;
            default:
                throw new RuntimeException("should never reach here");
        }
    }

    public static void handleMain(int choice) {
        assert state == State.MAIN;

        switch (choice) {
            case 1:
                startGame();
                printGameMenu();
                state = State.TURN;
                break;
            case 2:
                printOptions();
                state = State.OPTION;
                break;
            case 3:
                //fix it later :D
                state = State.EXIT;
            default:
                System.out.println(String.format(ERROR_NUM, choice));
        }
    }

    //TO DO:
    //make opponent configurable to be AI or human ran
    public static void handleOptions(int choice) {
        printOptions();
        throw new RuntimeException(ERROR_CUTE);
    }

    //Ends the program
    public static void handleExit(int choice) {
        System.out.println("Bye.");
        if (game == null) {
            //System.exit();
        } else {
            game.stopGame();
            //System.exit();
        }
    }

    //player chooses next menu to enter
    public static void handleTurn(int choice) {
        assert state == State.TURN;
        //players only draw cards on non-starting turns
        if(!List.of(1,2).contains(game.turnCount)) {
            activePlayer.drawCard();
        }

        switch(choice) {
            case 1:
                if (activePlayer.getHand().isEmpty()) {
                    System.out.println("You have no cards to play.");
                } else {
                    printHandMenu();
                    state = State.HAND;
                }
                break;
            case 2:
                if (activePlayer.getActive() == null) {
                    System.out.println("You have no active Pokemon to attack with.");
                } else if (List.of(1,2).contains(game.turnCount)) {
                    System.out.println("You cannot attack on the starting turns.");
                } else {
                    printAttackMenu();
                    state = State.ATTACK;
                }
                break;
            case 3:
                if (activePlayer.getActive() == null) {
                    System.out.println("You have no active Pokemon to retreat.");
                } else {
                    printRetreatMenu();
                    state = State.RETREAT;
                }
                break;
            case 4:
                printGameState();
                break;
            case 5:
                //turn count check might be redundant
                if (List.of(1,2).contains(game.turnCount) && activePlayer.getActive() == null) {
                    System.out.println("You cannot end your turn without placing at least one Pokemon.");
                } else {
                    printPassMenu();
                    state = State.PASS;
                }
                break;
            default:
                System.out.println(String.format(ERROR_NUM, choice));
                break;
        }
    }

    //player chooses a card out of their hand and handles according to card type
    public static void handleHand(int choice) {
        assert state == State.HAND;

        try {
            String type = activePlayer.getHand().get(choice-1).getClass().getSuperclass().getSimpleName();

            switch (type) {
                case "Pokemon" :
                    handlePokemon((Pokemon) activePlayer.getHand().get(choice-1));
                    printGameMenu();
                    state = State.TURN;
                    break;
                case "Trainer" :
                    handleTrainer((Trainer) activePlayer.getHand().get(choice-1));
                    printGameMenu();
                    state = State.TURN;
                    break;
                case "Energy" :
                    handleEnergy((Energy) activePlayer.getHand().get(choice-1));
                    printGameMenu();
                    state = State.TURN;
                    break;
                default:
                    System.out.println(String.format(ERROR_NUM, choice));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format(ERROR_NUM, choice));
            printHandMenu();
        }
    }

    //player chooses a move to attack defending player with, their energy counter is reset and their turn ends
    public static void handleAttack(int choice) {

        //make sure choice is in bounds
        try {
            //energy costs must be met to use move
            if (activePlayer.getActive().getMoveSet().get(choice-1).costs.isMet(activePlayer.getActive())) {

                inactivePlayer.getActive().takeDamage(activePlayer.getActive().getMoveSet().get(choice-1).dmg);

                //check if defending pkmn gets KO'd
                if (inactivePlayer.getActive().getHp() < 0) {

                    game.knockOut(inactivePlayer);

                    if (game.checkLoser(inactivePlayer)) {
                        state = State.WINNER;
                        return;
                    }
                    state = State.KO;
                    return;
                }

                //reset energy counter
                activePlayer.setEnergyCounter(0);
                //check if its starting turns
                if(!List.of(1,2).contains(game.turnCount)) {
                    inactivePlayer.drawCard();
                }

                changeTurn();
                game.turnCount++;
                printGameMenu();
            } else {
                System.out.println("Not enough energy.");
                printGameMenu();
                state = State.TURN;
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format(ERROR_NUM, choice));
        }
    }

    //player picks pokemon card from bench to replace active pokemon
    public static void handleRetreat(int choice) {

        switch (choice) {
            case 1:
                //check if retreat cost is met
                if (activePlayer.getActive().getEnergyRes().size() < activePlayer.getActive().retreatCost) {
                    System.out.println("Not enough energy. " + activePlayer.getActive().toString() + " has " + activePlayer.getActive().getEnergyRes().size() + " energy attached.");
                } else {
                    //player chooses pkmn to replace the active one
                    System.out.println("Please choose a pokemon to replace " + activePlayer.getActive());
                    int i = 1;
                    for (Pokemon poke : activePlayer.getBench()) {
                        System.out.println(i + " - " + poke);
                    }

                    choice = PlayerInput.getInput();
                    try {
                        activePlayer.getBench().add(activePlayer.getActive());
                        activePlayer.setActive(activePlayer.getBench().get(choice-1));
                        activePlayer.getBench().remove(choice-1);

                        //removes energy from reservoir
                        for (int j = 0; j < activePlayer.getActive().retreatCost; j++) {
                            activePlayer.getActive().getEnergyRes().removeFirst();
                        }

                        System.out.println(activePlayer.getActive().toString() + "is now your active Pokemon.");

                        state = State.TURN;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(String.format(ERROR_NUM, choice));
                    }
                }
                break;
            case 2:
                printGameMenu();
                state = State.TURN;
                break;
            default:
                System.out.println(String.format(ERROR_NUM, choice));
            break;
        }
    }

    //asks player if they would like to pass turn without attacking and resets their energy counter to 0
    public static void handlePass(int choice) {

        switch (choice) {
            case 1:
                activePlayer.setEnergyCounter(0);
                //check if its starting turns
                if(!List.of(1,2).contains(game.turnCount)) {
                    inactivePlayer.drawCard();
                }
                changeTurn();
                game.turnCount++;
                printGameMenu();

                state = State.TURN;
                break;
            case 2:
                printGameMenu();
                state = State.TURN;
                break;
            default:
                System.out.println(String.format(ERROR_NUM, choice));
                break;
        }
    }

    //takes pokemon from hand, plays it to bench, automatically puts pkmn as active if empty
    public static void handlePokemon(Pokemon pokemon) {
        if (activePlayer.getActive() == null) {
            System.out.println("First Pokemon goes to active slot.");
            activePlayer.getHand().remove(pokemon);
            activePlayer.setActive(pokemon);
        } else if (activePlayer.getBench().size() == 5) {
            System.out.println("Your bench is full.");
        } else {
            System.out.println("Pokemon sent to bench");
            activePlayer.getHand().remove(pokemon);
            activePlayer.getBench().add(pokemon);
        }
    }

    //player picks pokemon to attach energy to
    public static void handleEnergy(Energy energy) {

        if (activePlayer.getEnergyCounter() > 0) {
            System.out.println("You have already played an energy this turn.");
        } else if (activePlayer.getBench().isEmpty() && activePlayer.getActive() == null) {
            System.out.println("You have no Pokemon to attach energy to.");
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
            } catch (IndexOutOfBoundsException e) {
                System.out.println(String.format(ERROR_NUM, choice));
            }
        }
    }

    //displays trainer card description and let player decide whether to play
    public static void handleTrainer(Trainer trainer) {
        printTrainerMenu(trainer);

        int choice = PlayerInput.getInput();

        switch (choice) {
            case 1:
                trainer.ability(activePlayer, inactivePlayer);
                break;
            case 2:
                break;
            default:
                System.out.println(String.format(ERROR_NUM, choice));
                break;
        }
    }

    //inactive player chooses a new active pkmn before turn change
    public static void handleKO(int choice) {
        try {
            inactivePlayer.setActive(inactivePlayer.getBench().get(choice-1));
            System.out.println(activePlayer.getActive().toString() + " set as active Pokemon.");
            changeTurn();
            state = State.TURN;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format(ERROR_NUM, choice));
        }
    }

    //TO DO:
    public static void handleWinner() {
        //should exit program
    }

    public static void changeTurn() {
        Player a = activePlayer;
        activePlayer = inactivePlayer;
        inactivePlayer = a;
    }

    public static void startGame() {

        ArrayList<PkmnCard> deck1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Pikachu pikachu = new Pikachu();
            deck1.add(pikachu);
        }
        for (int i = 0; i < 30; i++) {
            //ElectricEnergy electricEnergy = new ElectricEnergy();
            //deck1.add(electricEnergy);

            ProfessorsResearch professorsResearch = new ProfessorsResearch();
            deck1.add(professorsResearch);
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
    }

    public static void printMainMenu() {
        System.out.println("----- MAIN MENU -----");
        System.out.println("1 - Play Game");
        System.out.println("2 - Options");
        System.out.println("3 - End Game");
    }

    public static void printOptions() {
        System.out.println("----- OPTIONS -----");
    }

    public static void printGameMenu() {
        System.out.println("----- GAME MENU -----");
        System.out.println("1 - Play Card from Hand");
        System.out.print(printHand());
        System.out.println();
        System.out.println("2 - Attack PKMN");
        System.out.println("3 - Retreat PKMN");
        System.out.println("4 - Check PKMN");
        System.out.println("5 - Pass");
    }

    public static void printHandMenu() {
        System.out.println("----- YOUR HAND -----");
        int i = 1;
        for (PkmnCard card : activePlayer.getHand()) {
            System.out.println(i + " - " + card.toString());
            i++;
        }
    }

    public static void printTrainerMenu(Trainer trainer) {
        System.out.println();
        System.out.println("----- " + trainer.toString() + " -----");
        System.out.println();
        System.out.println(trainer.desc);
        System.out.println();
        System.out.println("Would you like to use this card?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
    }

    public static void printAttackMenu(){
        System.out.println("Please choose an attack: ");
        System.out.println("----- MOVES -----");
        int i = 1;
        for (Attack move : activePlayer.getActive().getMoveSet()) {
            System.out.println(i + " - " + move.name + ": " + move.costsToString());
            System.out.println(move.desc);
            i++;
        }
    }

    public static void printRetreatMenu() {
        if(activePlayer.getActive() == null) {
            System.out.println("You have no Pokemon to retreat.");
            state = State.TURN;
        } else {
            System.out.println("Would you like to retreat? " + activePlayer.getActive().toString() + "'s retreat cost is " + activePlayer.getActive().retreatCost + " energy.");
            System.out.println("1 - Yes");
            System.out.println("2 - No");
        }
    }

    public static void printPassMenu() {
        System.out.println("End your turn without attacking?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
    }

    public static void printKOMenu() {
        System.out.println("Please choose a new Pokemon to be your active!");

        int i = 1;
        for (Pokemon pokemon : inactivePlayer.getBench()) {
            System.out.println(i + " - " + pokemon.toString());
            i++;
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

    //prints both players active pkmn HP and benched pkmn w/ HP and energyRes
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
            System.out.println(inactivePlayer.getActive().toString() + " HP: " + inactivePlayer.getActive().getHp() + " " + activePlayer.getActive().resToString());
        }

        System.out.println("Benched PKMN: ");
        if (inactivePlayer.getBench().isEmpty()) {
            System.out.println("None");
        } else {
            for (Pokemon pkmn : inactivePlayer.getBench()) {
                System.out.println(pkmn + " HP: " + pkmn.getHp() + " " + pkmn.resToString());
            }
        }
    }


}
