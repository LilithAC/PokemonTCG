package csci3327.pokemonproject.pkmncards.pokemon;

import csci3327.pokemonproject.pkmncards.Attack;
import csci3327.pokemonproject.pkmncards.energy.EnergyType;

import java.util.ArrayList;

public class Snom extends Pokemon {

    public Snom() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 50;
        type = EnergyType.WATER;
        retreatCost = 1;

        Attack ram = new Attack("Ram", 10, 10, 1, 0, EnergyType.ANY, "[ 1 TOTAL energy, 1 ANY energy ]");
        moveSet.add(ram);
    }

    @Override
    public String toString() {
        return "Snom";
    }
}
