import java.util.ArrayList;

public class Pikachu extends Pokemon {

    public Pikachu() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();
        moveSet.add("Scratch");
        moveSet.add("Quick Attack");
        hp = 60;
        type = "Electric";
        retreatCost = 1;
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

    @Override
    public String toString() {
        return "Pikachu";
    }
}
