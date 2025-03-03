package csci3327.pokemonproject.pkmncards.pokemon;

import csci3327.pokemonproject.pkmncards.Attack;
import csci3327.pokemonproject.pkmncards.energy.EnergyType;

import java.util.ArrayList;

public class Pikachu extends Pokemon {

    public Pikachu() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 60;
        type = EnergyType.ELECTRIC;
        retreatCost = 1;

        Attack scratch = new Attack("Scratch", 20, 20, 2, 0, EnergyType.ANY, "[ 2 TOTAL energy, 2 ANY energy ]");
        Attack quickAttack = new Attack("Quick Attack", 20, 30, 2, 1, EnergyType.ELECTRIC, "[ 2 TOTAL energy, 1 ELECTRIC energy ]");
        moveSet.add(scratch);
        moveSet.add(quickAttack);
    }

    @Override
    public String toString() {
        return "Pikachu";
    }
}