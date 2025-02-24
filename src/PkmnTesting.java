import java.util.ArrayList;

public class PkmnTesting {

    public static void main(String[] args) {

        WaterEnergy waterEnergy = new WaterEnergy();
        Pikachu pikachu = new Pikachu();


        ArrayList<PkmnCard> deck1 = new ArrayList<>();
        for (int i = 0; i < 59; i++) {
            deck1.add(waterEnergy);
        }
        deck1.add(pikachu);

        ArrayList<PkmnCard> deck2 = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            deck2.add(pikachu);
        }
        
        Player player1 = new Player(deck1);
        Player player2 = new Player(deck2);

        PkmnGame test = new PkmnGame(player1, player2);
        test.startGame();

        // ===== THIRD CHECKUP =====
        //1-4 different pokemon minimum
        //1-5 different trainer cards
        //as much energy as desired
        //Sample: 20 Pokemon, 30 trainers, 10 energy

        //you should be able to play against yourself OR an ai that does everything it can do and ends turn
        //make javaDoc, label extra features

    }
}
