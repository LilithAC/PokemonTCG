package csci3327.pokemonproject.pkmncards.pokemon;

import csci3327.pokemonproject.pkmncards.Attack;
import csci3327.pokemonproject.pkmncards.energy.EnergyType;

import java.util.ArrayList;

public class Leafeon extends Pokemon {

    public Leafeon() {
        energyRes = new ArrayList<>();
        moveSet = new ArrayList<>();

        hp = 90;
        type = EnergyType.GRASS;
        retreatCost = 2;

        Attack spiralDrain = new Attack("Spiral Drain", 40, 40, 2, 1, EnergyType.GRASS, "[ 2 TOTAL energy, 1 GRASS energy ]");
        Attack magicalLeaf = new Attack("Magical Leaf", 50, 80,3,1, EnergyType.GRASS, "[ 3 TOTAL energy, 1 GRASS energy ]");
        magicalLeaf.desc = "Flip a coin. If heads this attack deals 80 damage instead.";

        moveSet.add(spiralDrain);
        moveSet.add(magicalLeaf);
    }

    @Override
    public String toString() {
        return "Leafeon";
    }
}
