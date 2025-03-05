package csci3327.pokemonproject.app;

import csci3327.pokemonproject.pkmncards.*;
import csci3327.pokemonproject.pkmncards.energy.Energy;
import csci3327.pokemonproject.pkmncards.energy.GrassEnergy;
import csci3327.pokemonproject.pkmncards.energy.PsychicEnergy;
import csci3327.pokemonproject.pkmncards.pokemon.*;
import csci3327.pokemonproject.pkmncards.trainer.*;

import java.util.ArrayList;
import java.util.List;

public class GameStateHandle {

    protected static Player activePlayer;
    protected static Player inactivePlayer;
    protected static PkmnGame game;
    protected static State state = State.MAIN;

    public static final String ERROR_NUM = "Invalid choice: %s. Must be a number listed.";
    public static final String ERROR_CUTE = "Not implemented yet :3";


    /**
     * Handles each different state the game can be in
     * @param choice the menu int chosen by the player
     */
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
                System.out.println("Bye.");
                handleExit();
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

    /**
     * Allows player to choose to start game, change options, or exit the program
     * @param choice the menu int chosen by the player
     */
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
                state = State.EXIT;
                break;
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

    /**
     * Ends the program
     */
    public static void handleExit() {
        if (game == null) {
            System.exit(0);
        } else {
            game.stopGame();
            System.exit(0);
        }
    }

    /**
     * Allows player to either play a card from their hand, attack the inactive player's
     * active pokemon, retreat their active pokemon, or pass their turn without attacking
     * @param choice the menu int chosen by the player
     */
    public static void handleTurn(int choice) {
        assert state == State.TURN;

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
                } else if (activePlayer.getBench().isEmpty()) {
                    System.out.println("You have no Pokemon to retreat with.");
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

    /**
     * Allows player to choose a card out of their hand
     * and handles according to card type
     * @param choice the menu int chosen by the player
     */
    public static void handleHand(int choice) {
        assert state == State.HAND;

        //check if player chose the back button
        if (choice == activePlayer.getHand().size()+1) {
            printGameMenu();
            state = State.TURN;
            return;
        }

        try {
            String type = activePlayer.getHand().get(choice-1).getClass().getSuperclass().getSimpleName();

            switch (type) {
                case "Pokemon" :
                    handlePokemon((Pokemon) activePlayer.getHand().get(choice-1));
                    printGameMenu();
                    state = State.TURN;
                    break;
                case "Trainer" :
                    if (handleTrainer((Trainer) activePlayer.getHand().get(choice-1))) {
                        activePlayer.getDiscard().add(activePlayer.getHand().get(choice-1));
                        activePlayer.getHand().remove(choice-1);
                    }
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

    /**
     * Allows player to choose a move to attack defending player with
     * their energy counter is reset and their turn ends.
     * @param choice the menu int chosen by the player
     */
    public static void handleAttack(int choice) {

        //check if player chose back button
        if (choice == activePlayer.getActive().getMoveSet().size()+1) {
            printGameMenu();
            state = State.TURN;
            return;
        }

        //make sure choice is in bounds
        try {
            //energy costs must be met to use move
            if (activePlayer.getActive().getMoveSet().get(choice-1).getCosts().isMet(activePlayer.getActive())) {

                //flip coin to see if critical
                Coin coin = new Coin();
                if (coin.flip()) {
                    inactivePlayer.getActive().takeDamage(activePlayer.getActive().getMoveSet().get(choice - 1).getCritDmg());
                    System.out.println(inactivePlayer.getActive().toString() + " took " + activePlayer.getActive().getMoveSet().get(choice - 1).getCritDmg() + " damage!");
                } else {
                    inactivePlayer.getActive().takeDamage(activePlayer.getActive().getMoveSet().get(choice - 1).getDmg());
                    System.out.println(inactivePlayer.getActive().toString() + " took " + activePlayer.getActive().getMoveSet().get(choice - 1).getDmg() + " damage!");
                }

                //check if defending pkmn gets KO'd
                if (inactivePlayer.getActive().getHp() <= 0) {

                    game.knockOut(inactivePlayer);
                    activePlayer.removePrize();

                    //check if player 1 took all prize cards
                    if(game.checkWin(activePlayer)) {
                        game.gameOver(activePlayer);
                        state = State.WINNER;
                        return;
                        //check if player 2's deck is empty or has no benched pokemon
                    } else if (game.checkLoser(inactivePlayer)) {
                        game.gameOver(activePlayer);
                        state = State.WINNER;
                        return;
                    } else {
                        printKOMenu();
                        state = State.KO;
                        return;
                    }
                }

                activePlayer.setEnergyCounter(0);
                //check if its starting turns
                if(!List.of(1,2).contains(game.turnCount)) {
                    inactivePlayer.drawCard();
                }
                changeTurn();
                game.turnCount++;
                printGameMenu();
                state = State.TURN;
            } else {
                System.out.println("Not enough energy.");
                printGameMenu();
                state = State.TURN;
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format(ERROR_NUM, choice));
        }
    }

    /**
     * Allows player to pick a pokemon card from bench to replace active pokemon
     * @param choice the menu int chosen by the player
     */
    public static void handleRetreat(int choice) {

        switch (choice) {
            case 1:
                //check if retreat cost is met
                if (activePlayer.getActive().getEnergyRes().size() < activePlayer.getActive().getRetreatCost()) {
                    System.out.println("Not enough energy. " + activePlayer.getActive().toString() + " has " + activePlayer.getActive().getEnergyRes().size() + " energy attached.");
                    printGameMenu();
                    state = State.TURN;
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
                        for (int j = 0; j < activePlayer.getActive().getRetreatCost(); j++) {
                            activePlayer.getActive().getEnergyRes().removeFirst();
                        }

                        System.out.println(activePlayer.getActive().toString() + "is now your active Pokemon.");

                        printGameMenu();
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

    /**
     * asks player if they would like to pass turn without attacking
     * then resets their energy counter to 0 if yes.
     * @param choice the menu int chosen by the player
     */
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

    /**
     * takes pokemon from hand, plays it to bench
     * automatically puts pokemon as active if empty
     * @param pokemon the pokemon to be put in play
     */
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

    /**
     * Allows player to pick pokemon to attach energy to.
     * @param energy the energy to be attached to a pokemon
     */
    public static void handleEnergy(Energy energy) {

        if (activePlayer.getEnergyCounter() > 0) {
            System.out.println("You have already played an energy this turn.");
        } else if (activePlayer.getBench().isEmpty() && activePlayer.getActive() == null) {
            System.out.println("You have no Pokemon to attach energy to.");
        } else {
            ArrayList<Pokemon> choices = new ArrayList<>();
            choices.add(activePlayer.getActive());
            choices.addAll(activePlayer.getBench());

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

    /**
     * Displays trainer card description and let player decide whether to play.
     * @param trainer the trainer to be used
     * @return whether the card was used
     */
    public static boolean handleTrainer(Trainer trainer) {
        printTrainerMenu(trainer);

        int choice = PlayerInput.getInput();

        switch (choice) {
            case 1:
                trainer.ability(activePlayer, inactivePlayer);
                return true;
            case 2:
                return false;
            default:
                System.out.println(String.format(ERROR_NUM, choice));
                return false;

        }
    }

    /**
     * inactive player chooses a new active pokemon before turn change.
     * @param choice the menu int chosen by the player
     */
    public static void handleKO(int choice) {
        try {
            inactivePlayer.setActive(inactivePlayer.getBench().get(choice-1));
            inactivePlayer.getBench().remove(choice-1);
            System.out.println(inactivePlayer.getActive().toString() + " set as active Pokemon.");
            activePlayer.setEnergyCounter(0);
            changeTurn();
            printGameMenu();
            state = State.TURN;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(String.format(ERROR_NUM, choice));
        }
    }

    /**
     * Prints the winning player and stops the game in progress.
     */
    public static void handleWinner() {
        game.gameOver(activePlayer);
    }

    /**
     * Switches the active and inactive player for turn changes.
     */
    public static void changeTurn() {
        Player a = activePlayer;
        activePlayer = inactivePlayer;
        inactivePlayer = a;
    }

    /**
     * Creates 2 decks and fills them cards, assigns them to 2 players,
     * then initializes new game with the 2 players.
     */
    public static void startGame() {

        ArrayList<PkmnCard> deck1 = new ArrayList<>();
        fillDeck1(deck1);
        ArrayList<PkmnCard> deck2 = new ArrayList<>();
        fillDeck2(deck2);

        Player playerA = new Player(deck1, "Player 1");
        Player playerB = new Player(deck2, "Player 2");

        game = new PkmnGame(playerA, playerB);
        game.startGame();
    }

    /**
     * Prints the main menu.
     */
    public static void printMainMenu() {
        System.out.println("----- MAIN MENU -----");
        System.out.println("1 - Play Game");
        System.out.println("2 - Options");
        System.out.println("3 - End Game");
    }

    /**
     * Prints the options menu
     */
    public static void printOptions() {
        System.out.println("----- OPTIONS -----");
    }

    /**
     * Prints the game menu for a single turn.
     */
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

    /**
     * Prints the active players hand.
     */
    public static void printHandMenu() {
        System.out.println("----- YOUR HAND -----");
        int i = 1;
        for (PkmnCard card : activePlayer.getHand()) {
            System.out.println(i + " - " + card.toString());
            i++;
        }
        System.out.println("----- -----");
        System.out.println(i + " - Back");
    }

    /**
     * Prints the trainer menu with card name and description.
     * @param trainer the trainer card to be displayed
     */
    public static void printTrainerMenu(Trainer trainer) {
        System.out.println();
        System.out.println("----- " + trainer.toString() + " -----");
        System.out.println();
        System.out.println(trainer.getDesc());
        System.out.println();
        System.out.println("Would you like to use this card?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
    }

    /**
     * Prints the attack menu with active players active pokemon as moves
     */
    public static void printAttackMenu(){
        System.out.println("Please choose an attack: ");
        System.out.println("----- MOVES -----");
        int i = 1;
        for (Attack move : activePlayer.getActive().getMoveSet()) {
            System.out.println(i + " - " + move.getName() + ": " + move.getDmg() + " DMG " + move.getCostsName());
            System.out.println(move.getDesc());
            i++;
        }
        System.out.println();
        System.out.println(i + " - Back");
    }

    /**
     * Prints the retreat menu with the active players active pokemon's
     * retreat cost
     */
    public static void printRetreatMenu() {
        if(activePlayer.getActive() == null) {
            System.out.println("You have no Pokemon to retreat.");
            state = State.TURN;
        } else {
            System.out.println("Would you like to retreat? " + activePlayer.getActive().toString() + "'s retreat cost is " + activePlayer.getActive().getRetreatCost() + " energy.");
            System.out.println("1 - Yes");
            System.out.println("2 - No");
        }
    }

    /**
     * Prints the pass menu
     */
    public static void printPassMenu() {
        System.out.println("End your turn without attacking?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
    }

    /**
     * Prints the KO menu showing inactive players pokemon that got knocked out
     */
    public static void printKOMenu() {
        System.out.println("Please choose a new Pokemon to be your active!");

        int i = 1;
        for (Pokemon pokemon : inactivePlayer.getBench()) {
            System.out.println(i + " - " + pokemon.toString());
            i++;
        }
    }

    /**
     * Formats the names of cards as strings
     * @return ArrayList of formatted pokemon card names
     */
    public static ArrayList<String> printHand() {
        ArrayList<String> handF = new ArrayList<>();
        for (PkmnCard card : activePlayer.getHand()) {
            handF.add(card.toString());
        }
        return handF;
    }

    /**
     * prints both players active pokemon's
     * and benched pokemon's HP and energyRes
     */
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
            System.out.println(inactivePlayer.getActive().toString() + " HP: " + inactivePlayer.getActive().getHp() + " " + inactivePlayer.getActive().resToString());
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

    /**
     * Fills deck with deck1 load out
     * @param deck player deck with 60 cards
     */
    public static void fillDeck1(ArrayList<PkmnCard> deck) {
        //20 pokemon
        for (int i = 0; i < 10; i++) {
            Mismagius mismagius = new Mismagius();
            deck.add(mismagius);
        }
        for (int i = 0; i < 5; i++) {
            Shuppet shuppet = new Shuppet();
            deck.add(shuppet);
        }
        for (int i = 0; i < 5; i++) {
            Pumpkaboo pumpkaboo = new Pumpkaboo();
            deck.add(pumpkaboo);
        }

        //20 trainer cards
        for (int i = 0; i < 10; i++) {
            ProfessorsResearch professorsResearch = new ProfessorsResearch();
            deck.add(professorsResearch);
        }
        for (int i = 0; i < 10; i++) {
            Karen karen = new Karen();
            deck.add(karen);
        }

        //20 energy
        for (int i = 0; i < 20; i++) {
            PsychicEnergy psychicEnergy = new PsychicEnergy();
            deck.add(psychicEnergy);
        }

    }

    /**
     * Fills deck with deck2 load out
     * @param deck player deck with 60 cards
     */
    public static void fillDeck2(ArrayList<PkmnCard> deck) {

        //20 pokemon
        for (int i = 0; i < 10; i++) {
            Leafeon leafeon = new Leafeon();
            deck.add(leafeon);
        }
        for (int i = 0; i < 10; i++) {
            Rowlet rowlet = new Rowlet();
            deck.add(rowlet);
        }

        //20 trainer cards
        for (int i = 0; i < 10; i++) {
            Lillie lillie = new Lillie();
            deck.add(lillie);
        }
        for (int i = 0; i < 10; i++) {
            Potion potion = new Potion();
            deck.add(potion);
        }

        //20 energy
        for (int i = 0; i < 20; i++) {
            GrassEnergy grassEnergy = new GrassEnergy();
            deck.add(grassEnergy);
        }

    }

    /**
     * Fetches GameStateHandles current state
     * @return the current state of GameStateHandle
     */
    public static State getState() {
        return state;
    }
}
