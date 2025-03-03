import java.util.ArrayList;

public class Leafeon extends Pokemon {

    public Leafeon() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 90;
        type = EnergyType.GRASS;
        retreatCost = 2;

        Attack spiralDrain = new Attack("Spiral Drain", 40, 40, 2, 1, EnergyType.GRASS);
        Attack magicalLeaf = new Attack("Magical Leaf", 50, 80,3,1, EnergyType.GRASS);
        magicalLeaf.desc = "Flip a coin. If heads this attack deals 80 damage instead.";

        moveSet.add(spiralDrain);
        moveSet.add(magicalLeaf);
    }

    @Override
    public String toString() {
        return "Leafeon";
    }
}
