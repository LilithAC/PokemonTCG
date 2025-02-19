import java.util.ArrayList;

public class MulliganStats {

    public static void main(String[] args) {

        for (int i = 1; i < 5; i++) {
            System.out.printf("%.2f", rareCandyStats(i));
            System.out.print("% chance of bricked");
            System.out.println();
        }
    }

    //parameter pokemon is how many pokemon compared to energy in deck
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

            Player player1 = new Player(deck1);
            Player player2 = new Player(deck2);

            PkmnGameLoop test = new PkmnGameLoop(player1, player2);
            test.startGame();
            if (!test.player1.containsPkmn()) {
                count++;
            }
        }
        return (count / 10000) * 100;
    }

    // ===== SECOND CHECKUP =====
    //write program that checks if at least pokemon in hand, then check probability
    //that all rare candies are bricked (inside the prize pile) from 1 to 4
    //PRINT A FUCKING GRAPH !!!!!!!!!!! :D

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

            Player player1 = new Player(deck1);
            Player player2 = new Player(deck2);

            //creates a new game and starts until player1 draws a hand with at least 1 pkmn
            do {
                PkmnGameLoop test = new PkmnGameLoop(player1, player2);
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
