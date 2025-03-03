import java.util.ArrayList;

public class Shuppet extends Pokemon {

    public Shuppet(){
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 50;
        type = EnergyType.PSYCHIC;

        Attack bleh = new Attack("Bleh", 10, 10,1, 1, EnergyType.PSYCHIC);
        moveSet.add(bleh);
    }

    @Override
    public String toString() {
        return "Shuppet";
    }
}
