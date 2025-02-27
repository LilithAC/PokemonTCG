import java.util.ArrayList;

public class Pikachu extends Pokemon {

    public Pikachu() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        Attack scratch = new Attack("Scratch", 20, 20, 2, 0, EnergyType.ANY);
        Attack quickAttack = new Attack("Quick Attack", 20, 30, 2, 1, EnergyType.ELECTRIC);

        moveSet.add(scratch);
        moveSet.add(quickAttack);

        hp = 60;
        type = EnergyType.ELECTRIC;
        retreatCost = 1;
    }

    @Override
    public String toString() {
        return "Pikachu";
    }
}