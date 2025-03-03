import java.util.ArrayList;

public class Snom extends Pokemon {

    public Snom() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 50;
        type = EnergyType.WATER;
        retreatCost = 1;

        Attack ram = new Attack("Ram", 10, 10, 1, 0, EnergyType.ANY);
        moveSet.add(ram);
    }

    @Override
    public String toString() {
        return "Snom";
    }
}
