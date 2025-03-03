package csci3327.pokemonproject.pkmncards.energy;

public class ElectricEnergy extends Energy {

    public ElectricEnergy() {
        type = EnergyType.ELECTRIC;
    }

    @Override
    public String toString() {
        return "Electric Energy";
    }
}
