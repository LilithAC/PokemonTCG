import java.util.ArrayList;

public class Leafeon extends Pokemon {

    //nbnnhmkgvfgfgfghgnvvvcvb vvfgnjhbbbhn mnngbbbmnhnnbuh <-- yuki written code
    public Leafeon() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        Attack spiralDrain = new Attack("Spiral Drain", 40, 40, 2, 1, EnergyType.GRASS);
        Attack magicalLeaf = new Attack("Magical Leaf", 50, 80,3,1, EnergyType.GRASS);

        moveSet.add(spiralDrain);
        moveSet.add(magicalLeaf);

        hp = 90;
        type = EnergyType.GRASS;
        retreatCost = 2;
    }

    @Override
    public String toString() {
        return "Leafeon";
    }
}
