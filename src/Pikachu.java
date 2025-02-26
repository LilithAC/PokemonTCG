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

    public boolean scratch(Pokemon poke) {
        if (energyCheck() > 0) {
            poke.takeDamage(20);
            return true;
        } else {
            System.out.println("Not enough energy.");
            return false;
        }
    }

    public boolean quickAttack(Pokemon poke) {
        if (energyCheck() > 1 && energyCheckType("Electric") > 0) {
            Coin coin = new Coin();
            if (coin.flip()) {
                poke.takeDamage(30);
                return true;
            } else {
                poke.takeDamage(20);
                return true;
            }
        } else {
            System.out.println("Not enough energy.");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Pikachu";
    }
}
