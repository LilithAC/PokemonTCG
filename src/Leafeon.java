import java.util.ArrayList;

public class Leafeon extends Pokemon {

    //nbnnhmkgvfgfgfghgnvvvcvb vvfgnjhbbbhn mnngbbbmnhnnbuh <-- yuki written code
    public Leafeon() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        Attack spiralDrain = new Attack("Spiral Drain", 40, 40);
        EnergyCost sd1 = new EnergyCost(EnergyType.ANY, 2);
        EnergyCost sd2 = new EnergyCost(EnergyType.GRASS, 1);
        spiralDrain.energyCosts.add(sd1);
        spiralDrain.energyCosts.add(sd2);

        Attack magicalLeaf = new Attack("Magical Leaf", 50, 80);
        EnergyCost ml1 = new EnergyCost(EnergyType.ANY, 3);
        EnergyCost ml2 = new EnergyCost(EnergyType.GRASS, 1);
        magicalLeaf.energyCosts.add(ml1);
        magicalLeaf.energyCosts.add(ml2);

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
