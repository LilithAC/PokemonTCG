import java.util.ArrayList;

public class Pikachu extends Pokemon {

    private int hp;
    private String type;
    private ArrayList<Energy> energy;

    public Pikachu() {
        energy = new ArrayList<>();
        hp = 60;
        type = "Electric";
    }

    public void scratch(Pokemon poke) {
        if (energyCheck() > 0) {
            poke.takeDamage(20);
        } else {
            System.out.println("Not enough energy.");
        }
    }

    public void quickAttack(Pokemon poke) {
        if (energyCheck() > 1 && energyCheckType("Electric") > 0) {
            Coin coin = new Coin();
            if (coin.flip()) {
                poke.takeDamage(30);
            } else { poke.takeDamage(20); }
        } else { System.out.println("Not enough energy."); }
    }

}
