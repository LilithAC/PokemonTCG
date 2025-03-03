import java.util.ArrayList;

public class Pumpkaboo extends Pokemon {

    public Pumpkaboo() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 60;
        type = EnergyType.PSYCHIC;
        retreatCost = 2;

        Attack seedBomb = new Attack("Seed Bomb", 10, 10, 1,1, EnergyType.ANY);
        Attack recklessCharge = new Attack("Reckless Charge", 40, 40, 3,3, EnergyType.ANY);

        moveSet.add(seedBomb);
        moveSet.add(recklessCharge);
    }

    @Override
    public String toString() {
        return "Pumpkaboo";
    }
}
