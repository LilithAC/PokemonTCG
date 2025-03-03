package csci3327.pokemonproject.pkmncards.energy;

public class PsychicEnergy extends Energy {

    public PsychicEnergy() {
        type = EnergyType.PSYCHIC;
    }

    @Override
    public String toString() {
        return "Psychic Energy";
    }
}
