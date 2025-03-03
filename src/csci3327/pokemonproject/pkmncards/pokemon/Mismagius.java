package csci3327.pokemonproject.pkmncards.pokemon;

import csci3327.pokemonproject.pkmncards.Attack;
import csci3327.pokemonproject.pkmncards.energy.EnergyType;

import java.util.ArrayList;

public class Mismagius extends Pokemon {

    public Mismagius() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 80;
        type = EnergyType.PSYCHIC;
        retreatCost = 2;

        Attack phychicRemoval = new Attack("Phychic Removal", 20, 20, 1, 1,EnergyType.PSYCHIC, "[ 1 TOTAL energy, 1 PSYCHIC energy ]");
        Attack grudge = new Attack("Grudge", 20, 50, 2, 1, EnergyType.PSYCHIC, "[ 2 TOTAL energy, 1 PSYCHIC energy ]");
        grudge.desc = "Flip a coin. If heads this attack does 50 damage instead.";

        moveSet.add(phychicRemoval);
        moveSet.add(grudge);
    }

    @Override
    public String toString() {
        return "Mismagius";
    }
}
