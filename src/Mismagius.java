import java.util.ArrayList;

public class Mismagius extends Pokemon {

    public Mismagius() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 80;
        type = EnergyType.PSYCHIC;
        retreatCost = 1;

        Attack phychicRemoval = new Attack("Phychic Removal", 10, 10, 1, 1,EnergyType.PSYCHIC);
        Attack grudge = new Attack("Grudge", 20, 50, 2, 1, EnergyType.PSYCHIC);
        moveSet.add(phychicRemoval);
        moveSet.add(grudge);
    }

    @Override
    public String toString() {
        return "Mismagius";
    }
}
