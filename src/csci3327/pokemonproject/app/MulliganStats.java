package csci3327.pokemonproject.app;

import csci3327.pokemonproject.pkmncards.pokemon.Pikachu;
import csci3327.pokemonproject.pkmncards.PkmnCard;
import csci3327.pokemonproject.pkmncards.trainer.RareCandy;
import csci3327.pokemonproject.pkmncards.energy.WaterEnergy;

import java.util.ArrayList;

public class MulliganStats {

    public static void main(String[] args) {

        //runs experiment from 1 to 4 rare candies in deck
        for (int i = 1; i < 5; i++) {
            System.out.printf("%.2f", rareCandyStats(i));
            System.out.print("% chance of bricked");
            System.out.println();
        }

        //runs experiment from 1 to 60 pokemon in deck
        for (int i = 1; i < 61; i++) {
            System.out.printf("%.2f", mulliganStats(i));
            System.out.print("% chance of a mulligan");
            System.out.println();
        }
    }

    /**
     * Runs a Monte Carlo simulation 10000 times to see the likelihood
     * of a player having to mulligan on their first turn.
     * @param pokemon amount of pokemon in deck
     * @return the likelihood of player having to mulligan
     */
    public static double mulliganStats(int pokemon) {
        WaterEnergy waterEnergy = new WaterEnergy();
        Pikachu pikachu = new Pikachu();

        //counter for total amount of mulligans
        double count = 0;
        for (int i = 0; i < 10000; i++) {
            //populate deck with specified amount of pkmn
            ArrayList<PkmnCard> deck1 = new ArrayList<>();
            for (int j = 0; j < pokemon; j++) {
                deck1.add(pikachu);
            }

            //if deck length is less than 60, populated the rest of the space with water energy
            if (deck1.size() < 60) {
                for (int j = deck1.size(); j < 60; j++) {
                    deck1.add(waterEnergy);
                }
            }

            //2nd player's deck initilized with only piakchus
            ArrayList<PkmnCard> deck2 = new ArrayList<>();
            for (int j = 0; j < 60; j++) {
                deck2.add(pikachu);
            }

            Player player1 = new Player(deck1, "Player 1");
            Player player2 = new Player(deck2, "Player 2");

            PkmnGame test = new PkmnGame(player1, player2);
            //test.startGame();
            if (!test.player1.containsPkmn()) {
                count++;
            }
        }
        return (count / 10000) * 100;
    }

    /**
     * Runs a Monte Carlo simulation 10000 times to see the likelihood
     * of all rare candies being in the prize pile.
     * @param rareCandies amount of rare candies in the deck
     * @return the likelihood of rare candies being in the prize pile
     */
    public static double rareCandyStats(int rareCandies) {

        WaterEnergy waterEnergy = new WaterEnergy();
        Pikachu pikachu = new Pikachu();
        RareCandy rareCandy = new RareCandy();

        double count = 0;
        for (int i = 0; i < 10000; i++) {

            //adds specified amount of rare candies
            ArrayList<PkmnCard> deck1 = new ArrayList<>();
            for (int j = 0; j < rareCandies; j++) {
                deck1.add(rareCandy);
            }

            for (int j = 0; j < 20; j++) {
                deck1.add(pikachu);
            }

            //populates the rest of the space with water energy
            if (deck1.size() < 60) {
                for (int j = deck1.size(); j < 60; j++) {
                    deck1.add(waterEnergy);
                }
            }

            //2nd player's deck initilized with only piakchus
            ArrayList<PkmnCard> deck2 = new ArrayList<>();
            for (int j = 0; j < 60; j++) {
                deck2.add(pikachu);
            }

            Player player1 = new Player(deck1, "Player 1");
            Player player2 = new Player(deck2, "Player 2");

            //creates a new game and starts until player1 draws a hand with at least 1 pkmn
            do {
                PkmnGame test = new PkmnGame(player1, player2);
                test.stopGame();
                test.startGame();
            } while (!player1.containsPkmn());

            if (player1.checkPrize(rareCandies)) {
                count++;
            }
        }
        return (count / 10000) * 100;
    }
}
